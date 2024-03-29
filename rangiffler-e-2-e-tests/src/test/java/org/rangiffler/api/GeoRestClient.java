package org.rangiffler.api;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.rangiffler.model.CountryJson;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class GeoRestClient extends BaseRestClient {
    public GeoRestClient() {
        super(CFG.getGeoUrl());
    }

    private final GeoService geoService = retrofit.create(GeoService.class);

    @Step("rangiffler-countries. Получить список стран")
    @Attachment
    public @Nonnull List<CountryJson> getCountries() {
        try {
            return Objects.requireNonNull(geoService.getCountries().execute().body());
        } catch (IOException e) {
            Assertions.fail("Can`t execute api call to rangiffler-countries: " + GeoRestClient.CFG.getGeoUrl() + " " + e.getMessage());
            return null;
        }
    }
}
