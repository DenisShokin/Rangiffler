package org.rangiffler.test.rest;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.api.PhotoRestClient;
import org.rangiffler.jupiter.annotation.GeneratePhoto;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.extension.GeneratePhotoExtension;
import org.rangiffler.jupiter.extension.GenerateUserExtension;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("[REST][rangiffler-photo]: Фото")
@ExtendWith({GenerateUserExtension.class, GeneratePhotoExtension.class})
public class PhotoRestTest extends BaseRestTest {

    private final PhotoRestClient photoRestClient = new PhotoRestClient();

    @AllureId("300001")
    @GenerateUser
    @GeneratePhoto
    @DisplayName("REST: При создании нового путешествия возвращается photoID из rangiffler-photo")
    @Test
    @Tag("REST")
    void apiShouldReturnIdOfAddPhoto(UserJson user, PhotoJson photo) {
        photo.setUsername(user.getUsername());
        final PhotoJson created = photoRestClient.addPhoto(photo);

        step("Проверить, что ответ содержит photoID (GUID)", () ->
                assertTrue(created.getId().toString().matches(ID_REGEXP))
        );
    }

}
