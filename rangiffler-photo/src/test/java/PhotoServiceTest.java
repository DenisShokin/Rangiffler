import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.rangiffler.data.CountryEntity;
import org.rangiffler.data.PhotoEntity;
import org.rangiffler.data.repository.PhotoRepository;
import org.rangiffler.model.CountryJson;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.service.CountryService;
import org.rangiffler.service.PhotoService;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PhotoServiceTest {

    private final String firstUsername = "first_username";
    private final String secondUsername = "second_username";
    private final String updateCountryCode = "IN";
    private final String updateCountryName = "India";
    private final String updateDescription = "Update description";
    private PhotoEntity firstPhotoEntity, secondPhotoEntity;
    private PhotoJson firstPhotoJson, secondPhotoJson, thirdPhotoJson;
    private CountryJson firstCountryJson;

    @Mock
    private PhotoRepository photoRepository;

    @InjectMocks
    private PhotoService photoService;

    @Mock
    private CountryService countryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        UUID firstPhotoId = UUID.randomUUID();

        firstPhotoEntity = new PhotoEntity();
        firstPhotoEntity.setId(firstPhotoId);
        firstPhotoEntity.setUsername(firstUsername);
        String photoStringValue = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/";
        firstPhotoEntity.setPhoto(photoStringValue.getBytes());
        CountryEntity firstCountryEntity = new CountryEntity();
        firstCountryEntity.setPhotoId(firstPhotoId);
        firstCountryEntity.setCode("KZ");
        firstCountryEntity.setName("Kazakhstan");
        firstPhotoEntity.setCountry(firstCountryEntity);

        firstPhotoJson = new PhotoJson();
        firstPhotoJson.setId(firstPhotoId);
        firstPhotoJson.setDescription("KZ description");
        firstPhotoJson.setPhoto(photoStringValue);
        firstPhotoJson.setUsername(firstUsername);
        firstCountryJson = new CountryJson();
        firstCountryJson.setName("Kazakhstan");
        firstCountryJson.setCode("KZ");
        firstPhotoJson.setCountryJson(firstCountryJson);

        UUID secondPhotoId = UUID.randomUUID();
        secondPhotoEntity = new PhotoEntity();
        secondPhotoEntity.setId(secondPhotoId);
        secondPhotoEntity.setUsername(secondUsername);
        secondPhotoEntity.setPhoto(photoStringValue.getBytes());
        CountryEntity secondCountryEntity = new CountryEntity();
        secondCountryEntity.setPhotoId(firstPhotoId);
        secondCountryEntity.setCode("KE");
        secondCountryEntity.setName("Kenya");
        secondPhotoEntity.setCountry(secondCountryEntity);

        secondPhotoJson = new PhotoJson();
        secondPhotoJson.setId(firstPhotoId);
        secondPhotoJson.setDescription("KE description");
        secondPhotoJson.setPhoto(photoStringValue);
        secondPhotoJson.setUsername(secondUsername);
        CountryJson secondCountryJson = new CountryJson();
        secondCountryJson.setName("Kenya");
        secondCountryJson.setCode("KE");
        secondPhotoJson.setCountryJson(secondCountryJson);
    }

    @Test
    public void getAllUserPhotosTest() {
        List<PhotoEntity> photoEntities = new ArrayList<>();
        photoEntities.add(firstPhotoEntity);

        Mockito.when(photoRepository.findAllByUsername(firstUsername)).thenReturn(photoEntities);
        Mockito.when(countryService.getCountryByCode(firstPhotoEntity)).thenReturn(UUID.randomUUID());

        List<PhotoJson> result = photoService.getAllUserPhotos(firstUsername);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(firstPhotoEntity.getId(), result.get(0).getId());
        Assertions.assertEquals(firstPhotoEntity.getUsername(), result.get(0).getUsername());
    }

    @Test
    public void addPhotoTest() {
        Mockito.when(photoRepository.save(Mockito.any(PhotoEntity.class))).thenReturn(firstPhotoEntity);
        Mockito.when(countryService.getCountryByCode(firstPhotoEntity)).thenReturn(UUID.randomUUID());

        PhotoJson result = photoService.addPhoto(firstPhotoJson);

        Assertions.assertEquals(firstPhotoEntity.getId(), result.getId());
        Assertions.assertEquals(firstPhotoEntity.getDescription(), result.getDescription());
        Assertions.assertEquals(firstPhotoEntity.getUsername(), result.getUsername());
    }

    @Test
    public void addPhoto_ThrowExceptionWhenPhotoIsEmptyTest() {
        UUID thirdPhotoId = UUID.randomUUID();

        thirdPhotoJson = new PhotoJson();
        thirdPhotoJson.setId(thirdPhotoId);
        thirdPhotoJson.setDescription("Empty photo");
        thirdPhotoJson.setUsername(firstUsername);
        thirdPhotoJson.setPhoto("");
        CountryJson thirdCountryJson = new CountryJson();
        thirdCountryJson.setName("India");
        thirdCountryJson.setCode("in");
        thirdPhotoJson.setCountryJson(thirdCountryJson);

        Exception exception = assertThrows(ResponseStatusException.class, () -> photoService.addPhoto(thirdPhotoJson));
        Assertions.assertTrue(exception.getMessage().contains("Photo must not be empty!"));
    }

    @Test
    public void deletePhoto_ThrowExceptionWhenNonExistentIdTest() {
        Mockito.when(photoRepository.save(Mockito.any(PhotoEntity.class))).thenReturn(firstPhotoEntity);

        Exception exception = assertThrows(ResponseStatusException.class, () -> photoService.deletePhoto(secondPhotoEntity.getId()));
        Assertions.assertTrue(exception.getMessage().contains("Can`t find photo by given id: " + secondPhotoEntity.getId()));
    }

    @Test
    public void editPhotoTest() {
        PhotoEntity savedPhotoEntity = firstPhotoEntity;
        savedPhotoEntity.setDescription(updateDescription);
        savedPhotoEntity.getCountry().setName(updateCountryName);
        savedPhotoEntity.getCountry().setCode(updateCountryCode);

        Mockito.when(photoRepository.findById(firstPhotoEntity.getId())).thenReturn(Optional.of(firstPhotoEntity));
        Mockito.when(photoRepository.save(Mockito.any(PhotoEntity.class))).thenReturn(savedPhotoEntity);
        Mockito.when(countryService.getCountryByCode(firstPhotoEntity)).thenReturn(UUID.randomUUID());

        PhotoJson photoJson = firstPhotoJson;
        firstPhotoJson.setDescription(updateDescription);
        CountryJson countryJson = firstCountryJson;
        countryJson.setName(updateCountryName);
        countryJson.setCode(updateCountryCode);
        PhotoJson result = photoService.editPhoto(photoJson);

        Assertions.assertEquals(photoJson.getId(), result.getId());
        Assertions.assertEquals(updateDescription, result.getDescription());
        Assertions.assertEquals(updateCountryCode, result.getCountryJson().getCode());
        Assertions.assertEquals(updateCountryName, result.getCountryJson().getName());
    }

    @Test
    public void editPhoto_ThrowsExceptionWhenPhotoNotFoundTest() {
        Mockito.when(photoRepository.findById(secondPhotoJson.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResponseStatusException.class, () -> photoService.editPhoto(secondPhotoJson));
        Assertions.assertTrue(exception.getMessage().contains("Can`t find photo by given id: " + secondPhotoJson.getId()));
    }

    @Test
    public void editPhoto_ThrowsExceptionWhenPhotoUsernameMismatchTest() {
        PhotoEntity savedPhotoEntity = firstPhotoEntity;
        savedPhotoEntity.setDescription(updateDescription);
        savedPhotoEntity.getCountry().setName(updateCountryName);
        savedPhotoEntity.getCountry().setCode(updateCountryCode);

        Mockito.when(photoRepository.findById(firstPhotoEntity.getId())).thenReturn(Optional.of(firstPhotoEntity));
        Mockito.when(photoRepository.save(Mockito.any(PhotoEntity.class))).thenReturn(savedPhotoEntity);

        PhotoJson photoJson = firstPhotoJson;
        firstPhotoJson.setUsername(secondUsername);
        firstPhotoJson.setDescription(updateDescription);
        CountryJson countryJson = firstCountryJson;
        countryJson.setName(updateCountryName);
        countryJson.setCode(updateCountryCode);

        Exception exception = assertThrows(ResponseStatusException.class, () -> photoService.editPhoto(photoJson));
        Assertions.assertTrue(exception.getMessage().
                contains("Photo with id = " + photoJson.getPhoto() + "already connect with other username"));
    }

}