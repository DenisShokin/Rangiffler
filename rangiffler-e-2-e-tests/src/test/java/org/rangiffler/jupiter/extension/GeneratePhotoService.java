package org.rangiffler.jupiter.extension;

import org.rangiffler.api.GeoRestClient;
import org.rangiffler.jupiter.annotation.GeneratePhoto;
import org.rangiffler.model.CountryJson;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.utils.ImageUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GeneratePhotoService {

    private static final GeoRestClient geoRestClient = new GeoRestClient();
    private static List<CountryJson> countries;
    private static List<String> images = new ArrayList<>();

    static {
        countries = geoRestClient.getCountries();

        images.add("src/test/resources/testdata/country/canada_1.jfif");
        images.add("src/test/resources/testdata/country/china_1.jfif");
        images.add("src/test/resources/testdata/country/germany_1.jfif");
        images.add("src/test/resources/testdata/country/turkey_1.jpg");
    }

    public PhotoJson generatePhoto(@Nonnull GeneratePhoto annotation, String username) {
        PhotoJson photo = createRandomPhoto(username);
        return photo;
    }


    private PhotoJson createRandomPhoto(String username) {
        PhotoJson photo = new PhotoJson();
        CountryJson country = new CountryJson();
        Random rand = new Random();

        UUID id = UUID.randomUUID();
        photo.setId(id);
        photo.setUsername(username);
        String randomImage = images.get(rand.nextInt(images.size()));
        photo.setPhoto(ImageUtils.getDataURI(randomImage));
        photo.setDescription("Description for photo: " + new File(randomImage).getName());

        CountryJson randomElement = countries.get(rand.nextInt(countries.size()));
        country.setId(randomElement.getId());
        country.setName(randomElement.getName());
        country.setCode(randomElement.getCode());
        photo.setCountryJson(country);
        return photo;
    }

}