package org.rangiffler.jupiter.extension;

import com.github.javafaker.Faker;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.rangiffler.db.dao.user.RangifflerUsersDAO;
import org.rangiffler.db.dao.user.RangifflerUsersDAOHibernate;
import org.rangiffler.db.entity.user.Authority;
import org.rangiffler.db.entity.user.AuthorityEntity;
import org.rangiffler.db.entity.user.UserEntity;
import org.rangiffler.jupiter.annotation.GenerateUserAuthData;

import java.util.Arrays;
import java.util.Objects;

public class GenerateUserAuthDataExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace
            .create(GenerateUserAuthDataExtension.class);

    private final Faker faker = new Faker();

    @Override
    public void afterTestExecution(ExtensionContext context) {
        RangifflerUsersDAO usersDAO = new RangifflerUsersDAOHibernate();
        String testId = getTestId(context);
        UserEntity user = (UserEntity) context.getStore(NAMESPACE).get(testId);
        usersDAO.removeUser(user);
    }

    @Override
    public void beforeEach(ExtensionContext context) {

        final String testId = getTestId(context);

        GenerateUserAuthData annotation = context.getRequiredTestMethod()
                .getAnnotation(GenerateUserAuthData.class);

        if (annotation != null) {
            RangifflerUsersDAO usersDAO = new RangifflerUsersDAOHibernate();

            String username = annotation.username().isEmpty() ? faker.name().username() : annotation.username();

            UserEntity user = new UserEntity();
            user.setUsername(username);
            user.setPassword(annotation.password());
            user.setEnabled(annotation.enabled());
            user.setAccountNonExpired(annotation.accountNonExpired());
            user.setAccountNonLocked(annotation.accountNonLocked());
            user.setCredentialsNonExpired(annotation.credentialsNonExpired());
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
}
