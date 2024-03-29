package org.rangiffler.controller;

import org.rangiffler.model.PhotoJson;
import org.rangiffler.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class PhotoController {

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/photos")
    public List<PhotoJson> getPhotosForUser(@RequestParam String username) {
        return photoService.getAllUserPhotos(username);
    }

    @PostMapping("/photos")
    public PhotoJson addPhoto(@RequestBody PhotoJson photoJson) {
        return photoService.addPhoto(photoJson);
    }

    @DeleteMapping("/photos")
    public void deletePhoto(@RequestParam UUID photoId) {
        photoService.deletePhoto(photoId);
    }

    @PatchMapping("/photos/{id}")
    public PhotoJson editPhoto(@PathVariable UUID id, @RequestBody PhotoJson photoJson) {
        return photoService.editPhoto(photoJson);
    }

    @GetMapping("/friends/photos")
    public List<PhotoJson> getAllFriendsPhotos(@RequestParam String username) {
        return photoService.getAllFriendsPhotos(username);
    }


}
