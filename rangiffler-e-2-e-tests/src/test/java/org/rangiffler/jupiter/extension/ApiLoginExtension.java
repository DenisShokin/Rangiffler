package org.rangiffler.jupiter.extension;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;
import org.rangiffler.api.AuthClient;
import org.rangiffler.api.RetrofitAuthClient;
import org.rangiffler.api.context.CookieContext;
import org.rangiffler.api.context.SessionContext;
import org.rangiffler.api.util.OauthUtils;
import org.rangiffler.config.Config;
import org.rangiffler.jupiter.annotation.ApiLogin;

public class ApiLoginExtension implements BeforeEachCallback, AfterTestExecutionCallback {

    protected static final Config CFG = Config.getConfig();
    private final AuthClient authClient = new RetrofitAuthClient();
    private static final String JSESSIONID = "JSESSIONID";

    @Override
    public void beforeEach(ExtensionContext context) {
        ApiLogin apiLogin = context.getRequiredTestMethod().getAnnotation(ApiLogin.class);
        if (apiLogin != null) {
            doLogin(apiLogin.username(), apiLogin.password());
        }
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

    @Override
    public void afterTestExecution(ExtensionContext context) {
        SessionContext.getInstance().release();
        CookieContext.getInstance().release();
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
