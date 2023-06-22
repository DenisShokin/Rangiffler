package org.rangiffler.service;

import jakarta.annotation.Nonnull;
import org.rangiffler.data.CountryEntity;
import org.rangiffler.data.PhotoEntity;
import org.rangiffler.data.repository.PhotoRepository;
import org.rangiffler.model.PhotoJson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public List<PhotoJson> getAllUserPhotos(String username) {
        Map<UUID, PhotoJson> result = new HashMap<>();
        for (PhotoEntity photo : photoRepository.findAllByUsername(username)) {
            if (!result.containsKey(photo.getId())) {
                result.put(photo.getId(), PhotoJson.fromEntity(photo));
            }
        }
        return new ArrayList<>(result.values());
    }

    public PhotoJson addPhoto(PhotoJson photoJson) {
        PhotoEntity photoEntity = new PhotoEntity();
        UUID id = photoJson.getId() == null ? UUID.randomUUID() : photoJson.getId();

        photoEntity.setId(id);
        photoEntity.setDescription(photoJson.getDescription());
        photoEntity.setUsername(photoJson.getUsername());
        photoEntity.setPhoto(photoJson.getPhoto().getBytes());

        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setPhotoId(id);
        countryEntity.setCode(photoJson.getCountryJson().getCode());
        countryEntity.setName(photoJson.getCountryJson().getName());
        photoEntity.setCountry(countryEntity);

        return PhotoJson.fromEntity(photoRepository.save(photoEntity));
    }

    public void deletePhoto(UUID photoId) {
        // TODO: проверки при удалении
        photoRepository.deleteById(photoId);
    }

    public PhotoJson editPhoto(@Nonnull PhotoJson photoJson) {
        PhotoEntity photo = photoRepository.findPhotoEntityById(photoJson.getId());
        photo.setDescription(photoJson.getDescription());

        CountryEntity country = photo.getCountry();
        country.setName(photoJson.getCountryJson().getName());
        country.setCode(photoJson.getCountryJson().getCode());
        photo.setCountry(country);
        PhotoEntity saved = photoRepository.save(photo);

        return PhotoJson.fromEntity(saved);
    }
}
