package org.rangiffler.api;

import org.junit.jupiter.api.Assertions;
import org.rangiffler.model.PhotoJson;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Objects;

public class PhotoRestClient extends BaseRestClient {
    public PhotoRestClient() {
        super(CFG.getPhotoUrl());
    }

    private final PhotoService photoService = retrofit.create(PhotoService.class);

    public @Nonnull PhotoJson addPhoto(PhotoJson photo) {
        try {
            return Objects.requireNonNull(photoService.addPhoto(photo).execute().body());
        } catch (IOException e) {
            Assertions.fail("Can`t execute api call to rangiffler-photo: " + PhotoRestClient.CFG.getGeoUrl() + " " + e.getMessage());
            return null;
        }
    }

}
