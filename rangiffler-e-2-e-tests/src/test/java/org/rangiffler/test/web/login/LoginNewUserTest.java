package org.rangiffler.test.web.login;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rangiffler.db.dao.RangifflerUsersDAO;
import org.rangiffler.db.dao.RangifflerUsersDAOHibernate;
import org.rangiffler.db.entity.Authority;
import org.rangiffler.db.entity.AuthorityEntity;
import org.rangiffler.db.entity.UserEntity;
import org.rangiffler.page.LoginPage;
import org.rangiffler.page.StartPage;
import org.rangiffler.test.web.BaseWebTest;

import java.util.Arrays;

public class LoginNewUserTest extends BaseWebTest {
    private static Faker faker = new Faker();
    private StartPage startPage = new StartPage();
    private LoginPage loginPage = new LoginPage();
    private UserEntity user;
    private RangifflerUsersDAO usersDAO = new RangifflerUsersDAOHibernate();

    private static final String TEST_PWD = "12345";

    @BeforeEach
    void createUserForTest() {
        user = new UserEntity();
        user.setUsername(faker.name().name());
        user.setPassword(TEST_PWD);
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
    }

    @AfterEach
    void cleanUp() {
        usersDAO.removeUser(user);
    }

    @Test
    void loginTest() {
        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
        startPage.goToLogin();
        loginPage
                .checkThatPageLoaded()
                .successFillLoginForm(user.getUsername(), TEST_PWD)
                .checkThatPageLoaded();
    }

}
