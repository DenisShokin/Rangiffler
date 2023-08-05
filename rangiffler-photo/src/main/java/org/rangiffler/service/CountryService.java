package org.rangiffler.service;

import org.rangiffler.data.PhotoEntity;
import org.rangiffler.model.CountryJson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
public class CountryService {

    private final WebClient webClient;
    private final String rangifflerGeoBaseUri;

    public CountryService(WebClient webClient,
                          @Value("${rangiffler-geo.base-uri}") String rangifflerGeoBaseUri) {
        this.webClient = webClient;
        this.rangifflerGeoBaseUri = rangifflerGeoBaseUri;
    }

    public UUID getCountryIdByCode(PhotoEntity entity) {
        String countryCode = entity.getCountry().getCode();
        CountryJson country;
        URI uri = UriComponentsBuilder.fromHttpUrl(rangifflerGeoBaseUri + "/countries/" + countryCode).build().toUri();
        country = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(CountryJson.class)
                .block();

        return country.getId();
    }
}
