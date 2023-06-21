package org.rangiffler.controller;

import org.rangiffler.model.PhotoJson;
import org.rangiffler.service.api.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
  public List<PhotoJson> getPhotosForUser(@AuthenticationPrincipal Jwt principal) {
    String username = principal.getClaim("sub");
    return photoService.getAllUserPhotos(username);
  }
  @PostMapping("/photos")
  public PhotoJson addPhoto(@AuthenticationPrincipal Jwt principal, @RequestBody PhotoJson photoJson) {
    String username = principal.getClaim("sub");
    return photoService.addPhoto(username, photoJson);
  }

  //TODO: реализовать
  @GetMapping("/friends/photos")
  public List<PhotoJson> getAllFriendsPhotos() {
    return photoService.getAllFriendsPhotos();
  }

  //TODO: реализовать
  @PatchMapping("/photos/{id}")
  public PhotoJson editPhoto(@RequestBody PhotoJson photoJson) {
    return photoService.editPhoto(photoJson);
  }

  @DeleteMapping("/photos")
  public void deletePhoto(@RequestParam UUID photoId) {
    photoService.deletePhoto(photoId);
  }

}
