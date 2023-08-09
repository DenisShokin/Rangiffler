package org.rangiffler.service;

import jakarta.annotation.Nonnull;
import org.rangiffler.data.CountryEntity;
import org.rangiffler.data.PhotoEntity;
import org.rangiffler.data.repository.PhotoRepository;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final CountryService countryService;
    private final UserService userService;

    public PhotoService(PhotoRepository photoRepository,
                        CountryService countryService,
                        UserService userService) {
        this.photoRepository = photoRepository;
        this.countryService = countryService;
        this.userService = userService;
    }

    public List<PhotoJson> getAllUserPhotos(String username) {
        Map<UUID, PhotoJson> result = new HashMap<>();
        for (PhotoEntity photo : photoRepository.findAllByUsername(username)) {
            if (!result.containsKey(photo.getId())) {
                result.put(photo.getId(), PhotoJson.fromEntity(photo, countryService.getCountryIdByCode(photo)));
            }
        }
        return new ArrayList<>(result.values());
    }

    public PhotoJson addPhoto(@Nonnull PhotoJson photoJson) {
        if (photoJson.getPhoto().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Photo must not be empty!");
        }
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
        countryEntity.setPhoto(photoEntity);
        photoEntity.setCountry(countryEntity);

        return PhotoJson.fromEntity(photoRepository.save(photoEntity), countryService.getCountryIdByCode(photoEntity));
    }

    public void deletePhoto(@Nonnull UUID photoId) {
        Optional<PhotoEntity> photoById = photoRepository.findById(photoId);
        if (photoById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find photo by given id: " + photoId);
        } else {
            photoRepository.deleteById(photoId);
        }
    }

    public PhotoJson editPhoto(@Nonnull PhotoJson photoJson) {
        Optional<PhotoEntity> photoById = photoRepository.findById(photoJson.getId());
        if (photoById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find photo by given id: " + photoJson.getId());
        } else {
            PhotoEntity photo = photoById.get();
            if (photo.getId().equals(photoJson.getId()) &&
                    photo.getUsername().equals(photoJson.getUsername())) {
                photo.setDescription(photoJson.getDescription());

                CountryEntity country = photo.getCountry();
                country.setName(photoJson.getCountryJson().getName());
                country.setCode(photoJson.getCountryJson().getCode());
                photo.setCountry(country);
                PhotoEntity saved = photoRepository.save(photo);
                return PhotoJson.fromEntity(saved, countryService.getCountryIdByCode(saved));
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Photo with id = " + photoJson.getPhoto() + "already connect with other username");
            }
        }
    }

    public List<PhotoJson> getAllFriendsPhotos(@Nonnull String currentUser) {
        List<UserJson> friends = userService.getUserFriends(currentUser);
        List<String> friendsUsername = friends.stream().map(UserJson::getUsername).toList();
        Map<UUID, PhotoJson> result = new HashMap<>();
        for (String user : friendsUsername) {
            for (PhotoEntity photo : photoRepository.findAllByUsername(user)) {
                if (!result.containsKey(photo.getId())) {
                    result.put(photo.getId(), PhotoJson.fromEntity(photo, countryService.getCountryIdByCode(photo)));
                }
            }
        }
        return new ArrayList<>(result.values());
    }
}
