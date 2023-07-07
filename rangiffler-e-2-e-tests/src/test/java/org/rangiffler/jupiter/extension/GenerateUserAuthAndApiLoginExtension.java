package org.rangiffler.jupiter.extension;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.openqa.selenium.Cookie;
import org.rangiffler.api.AuthClient;
import org.rangiffler.api.RetrofitAuthClient;
import org.rangiffler.api.context.CookieContext;
import org.rangiffler.api.context.SessionContext;
import org.rangiffler.api.util.OauthUtils;
import org.rangiffler.config.Config;
import org.rangiffler.db.dao.user.RangifflerUsersDAO;
import org.rangiffler.db.dao.user.RangifflerUsersDAOHibernate;
import org.rangiffler.db.dao.userdata.RangifflerUsersDataDAOHibernate;
import org.rangiffler.db.entity.user.Authority;
import org.rangiffler.db.entity.user.AuthorityEntity;
import org.rangiffler.db.entity.user.UserEntity;
import org.rangiffler.db.entity.userdata.UserDataEntity;
import org.rangiffler.jupiter.annotation.GenerateUserAuthAndApiLogin;

import java.util.Arrays;
import java.util.Objects;

public class GenerateUserAuthAndApiLoginExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace
            .create(GenerateUserAuthAndApiLoginExtension.class);

    private final Faker faker = new Faker();

    protected static final Config CFG = Config.getConfig();
    private final AuthClient authClient = new RetrofitAuthClient();
    private static final String JSESSIONID = "JSESSIONID";

    @Override
    public void afterEach(ExtensionContext context) {
        SessionContext.getInstance().release();
        CookieContext.getInstance().release();

        RangifflerUsersDAO usersDAO = new RangifflerUsersDAOHibernate();
        String testId = getTestId(context);
        UserEntity user = (UserEntity) context.getStore(NAMESPACE).get(testId);
        usersDAO.removeUser(user);

        RangifflerUsersDataDAOHibernate usersDataDAO = new RangifflerUsersDataDAOHibernate();
        UserDataEntity userData = usersDataDAO.getUserByUsername(user.getUsername());
        usersDataDAO.removeUser(userData);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws InterruptedException {
        final String testId = getTestId(context);

        GenerateUserAuthAndApiLogin annotation = context.getRequiredTestMethod()
                .getAnnotation(GenerateUserAuthAndApiLogin.class);

        if (annotation != null) {
            RangifflerUsersDAO usersDAO = new RangifflerUsersDAOHibernate();

            String username = annotation.username().isEmpty() ? faker.name().username() : annotation.username();

            UserEntity user = new UserEntity();
            user.setUsername(username);
            user.setPassword(annotation.password());
            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setAuthorities(Arrays.stream(Authority.values()).map(
                    a -> {
                        AuthorityEntity ae = new AuthorityEntity();
                        ae.setAuthority(a);
                        ae.setUser(user);
                        return ae;
                    }
            ).toList());
            usersDAO.createUser(user);
            context.getStore(NAMESPACE).put(testId, user);
            doLogin(username, annotation.password());
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserEntity.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(getTestId(extensionContext), UserEntity.class);
    }

    private String getTestId(ExtensionContext context) {
        return Objects
                .requireNonNull(context.getRequiredTestMethod().getAnnotation(AllureId.class))
                .value();
    }

    private void doLogin(String username, String password) {
        final SessionContext sessionContext = SessionContext.getInstance();
        final String codeVerifier = OauthUtils.generateCodeVerifier();
        final String codeChallenge = OauthUtils.generateCodeChallange(codeVerifier);

        sessionContext.setCodeVerifier(codeVerifier);
        sessionContext.setCodeChallenge(codeChallenge);

        authClient.authorizePreRequest();
        authClient.login(username, password);
        final String token = authClient.getToken();
        setUpBrowser(token);
    }

    private void setUpBrowser(String token) {
        SessionContext sessionContext = SessionContext.getInstance();
        CookieContext cookieContext = CookieContext.getInstance();
        Selenide.open(CFG.getFrontUrl());
        Selenide.sessionStorage().setItem("id_token", token);
        Selenide.sessionStorage().setItem("codeChallenge", sessionContext.getCodeChallenge());
        Selenide.sessionStorage().setItem("codeVerifier", sessionContext.getCodeVerifier());
        Cookie jssesionIdCookie = new Cookie(JSESSIONID, cookieContext.getCookie(JSESSIONID));
        WebDriverRunner.getWebDriver().manage().addCookie(jssesionIdCookie);
    }

}
