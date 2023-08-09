package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.rangiffler.data.CountryEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@Component
public class CountryJson {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("code")
  private String code;

  @JsonProperty("name")
  private String name;

  public static CountryJson fromEntity(CountryEntity entity) {
    CountryJson countryJson = new CountryJson();
    countryJson.setName(entity.getName());
    countryJson.setCode(entity.getCode());
    return countryJson;
  }

}
