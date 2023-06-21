package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.rangiffler.data.PhotoEntity;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Data
public class PhotoJson {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("country")
  private CountryJson countryJson;

  @JsonProperty("photo")
  private String photo;

  @JsonProperty("description")
  private String description;

  @JsonProperty("username")
  private String username;

  public static PhotoJson fromEntity(PhotoEntity entity) {
    PhotoJson photo = new PhotoJson();
    CountryJson country = new CountryJson();

    country.setName(entity.getCountry().getName());
    country.setCode(entity.getCountry().getCode());

    photo.setId(entity.getId());
    photo.setCountryJson(country);
    //TODO: некорректноая передача изображения
    String photoByteString = new String(entity.getPhoto(), StandardCharsets.UTF_8);
    photo.setPhoto(photoByteString);
    photo.setDescription(entity.getDescription());
    photo.setUsername(entity.getUsername());

    return photo;
  }
}
