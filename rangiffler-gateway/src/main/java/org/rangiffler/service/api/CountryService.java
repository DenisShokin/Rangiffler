package org.rangiffler.service.api;

import org.rangiffler.model.CountryJson;
import org.rangiffler.model.UserJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
public class CountryService {

  private final WebClient webClient;
  private final String rangifflerGeoBaseUri;

  @Autowired
  public CountryService(WebClient webClient, @Value("${rangiffler-geo.base-uri}") String rangifflerGeoBaseUri) {
    this.webClient = webClient;
    this.rangifflerGeoBaseUri = rangifflerGeoBaseUri;
  }

  public List<CountryJson> getAllCountries() {
    URI uri = UriComponentsBuilder.fromHttpUrl(rangifflerGeoBaseUri + "/countries").build().toUri();

    return webClient.get()
            .uri(uri)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<CountryJson>>() {
            })
            .block();
  }

}
