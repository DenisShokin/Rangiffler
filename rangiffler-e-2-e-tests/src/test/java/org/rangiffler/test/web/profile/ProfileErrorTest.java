package org.rangiffler.test.web.profile;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.rangiffler.db.dao.user.RangifflerUsersDAO;
import org.rangiffler.db.dao.user.RangifflerUsersDAOHibernate;
import org.rangiffler.db.dao.userdata.RangifflerUsersDataDAOHibernate;
import org.rangiffler.db.entity.user.Authority;
import org.rangiffler.db.entity.user.AuthorityEntity;
import org.rangiffler.db.entity.user.UserEntity;
import org.rangiffler.db.entity.userdata.UserDataEntity;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.page.MainPage;
import org.rangiffler.page.ProfilePage;
import org.rangiffler.test.web.BaseWebTest;

import java.util.Arrays;

public class ProfileErrorTest extends BaseWebTest {

    private MainPage mainPage = new MainPage();
    private ProfilePage profilePage = new ProfilePage();
    private static UserEntity user;
    private static final RangifflerUsersDAO usersDAO = new RangifflerUsersDAOHibernate();
    private static final RangifflerUsersDataDAOHibernate usersDataDAO = new RangifflerUsersDataDAOHibernate();
    private static final String FIFTY_ONE_CHARACTERS = "123fsdfgsdfgsdfgsdfgsdfgsdfgsdfgsdfgwergrtehtrebgfa";
    private static final String TEST_USERNAME = "Clifton Rippin first";
    private static final String TEST_PWD = "123456";

    @BeforeAll
    static void createUserForTest() {
        user = new UserEntity();
        user.setUsername(TEST_USERNAME);
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

    @AfterAll
    static void cleanUp() {
        usersDAO.removeUser(user);
        UserDataEntity userData = usersDataDAO.getUserByUsername(user.getUsername());
        usersDataDAO.removeUser(userData);
    }

    @ApiLogin(username = TEST_USERNAME, password = TEST_PWD)
    @Test
    @AllureId("601")
    public void errorMessageShouldBeVisibleInCaseThatFirstnameMoreThan50Symbols() {
        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));

        profilePage = mainPage.checkThatPageLoaded()
                .clickEmptyPhotoProfileButton();
        profilePage.checkThatPageLoaded()
                .setFirstName(FIFTY_ONE_CHARACTERS)
                .checkFirstnameErrorMessage("Length of this field must be no longer than 50 characters");
    }

    @ApiLogin(username = TEST_USERNAME, password = TEST_PWD)
    @Test
    @AllureId("602")
    public void errorMessageShouldBeVisibleInCaseThatLastnameMoreThan50Symbols() {
        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));

        profilePage = mainPage.checkThatPageLoaded()
                .clickEmptyPhotoProfileButton();
        profilePage.checkThatPageLoaded()
                .setLastName(FIFTY_ONE_CHARACTERS)
                .checkLastnameErrorMessage("Length of this field must be no longer than 50 characters");
    }

}
