package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.rangiffler.data.CountryEntity;

import java.util.UUID;

@Data
public class CountryJson {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    public static CountryJson fromEntity(CountryEntity entity) {
        CountryJson country = new CountryJson();
        country.setId(entity.getId());
        country.setCode(entity.getCode());
        country.setName(entity.getName());
        return country;
    }

}
