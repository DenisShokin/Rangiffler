package org.rangiffler.service;

import jakarta.annotation.Nonnull;
import org.rangiffler.data.CountryEntity;
import org.rangiffler.data.repository.CountryRepository;
import org.rangiffler.model.CountryJson;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<CountryJson> getAllCountries() {
        Map<UUID, CountryJson> result = new HashMap<>();
        for (CountryEntity country : countryRepository.findAll()) {
            if (!result.containsKey(country.getId())) {
                result.put(country.getId(), CountryJson.fromEntity(country));
            }
        }
        return new ArrayList<>(result.values());
    }

    public CountryJson getCountryByCode(@Nonnull String code) {
        CountryEntity country = countryRepository.findCountryEntityByCode(code);
        if (country == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find country by given code: " + code);
        }
        return CountryJson.fromEntity(country);
    }
}
