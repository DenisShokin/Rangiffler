package org.rangiffler.test.web.login;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rangiffler.db.dao.user.RangifflerUsersDAO;
import org.rangiffler.db.dao.user.RangifflerUsersDAOHibernate;
import org.rangiffler.db.dao.userdata.RangifflerUsersDataDAOHibernate;
import org.rangiffler.db.entity.user.Authority;
import org.rangiffler.db.entity.user.AuthorityEntity;
import org.rangiffler.db.entity.user.UserEntity;
import org.rangiffler.db.entity.userdata.UserDataEntity;
import org.rangiffler.page.LoginPage;
import org.rangiffler.page.MainPage;
import org.rangiffler.page.StartPage;
import org.rangiffler.test.web.BaseWebTest;

import java.util.Arrays;

public class LoginNewUserTest extends BaseWebTest {
    private static Faker faker = new Faker();
    private StartPage startPage = new StartPage();
    private LoginPage loginPage = new LoginPage();
    private MainPage mainPage = new MainPage();
    private UserEntity user;
    private RangifflerUsersDAO usersDAO = new RangifflerUsersDAOHibernate();
    private RangifflerUsersDataDAOHibernate usersDataDAO = new RangifflerUsersDataDAOHibernate();
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
        UserDataEntity userData = usersDataDAO.getUserByUsername(user.getUsername());
        usersDataDAO.removeUser(userData);
    }

    @AllureId("401")
    @Test
    void loginTest() {
        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
        loginPage = startPage.goToLogin()
                .checkThatPageLoaded();
        mainPage = loginPage
                .successFillLoginForm(user.getUsername(), TEST_PWD)
                .checkThatPageLoaded();
    }

}
