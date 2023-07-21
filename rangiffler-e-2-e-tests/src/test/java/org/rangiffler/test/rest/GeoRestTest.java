package org.rangiffler.test.rest;

import io.qameta.allure.AllureId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.rangiffler.api.GeoRestClient;
import org.rangiffler.db.dao.geo.RangifflerGeoDAO;
import org.rangiffler.db.dao.geo.RangifflerGeoDAOHibernate;
import org.rangiffler.db.entity.geo.CountryEntity;
import org.rangiffler.model.CountryJson;

import java.util.List;
import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;

@DisplayName("[REST][rangiffler-geo]: Страны")
public class GeoRestTest extends BaseRestTest {

    private final GeoRestClient geoRestClient = new GeoRestClient();
    private final RangifflerGeoDAO geoDAO = new RangifflerGeoDAOHibernate();

    @AllureId("10001")
    @DisplayName("REST: При запросе countries возвращаются все страны из rangiffler-geo")
    @Test
    @Tag("REST")
    void apiShouldReturnAllCountries() {
        List<CountryJson> apiCountriesResponse = geoRestClient.getCountries();
        List<CountryEntity> dbCountries = geoDAO.getCountries();

        step("Проверить, что ответ /countries содержит все страны из БД", () -> Assertions
                .assertThat(apiCountriesResponse)
                .containsExactlyInAnyOrderElementsOf(dbCountries.stream()
                        .map(c -> new CountryJson(c.getId(), c.getCode(), c.getName())).collect(Collectors.toList())));
    }

}
