//package org.rangiffler.controller;
//
//import org.rangiffler.model.PhotoJson;
//import org.rangiffler.service.api.PhotoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//public class PhotoController {
//
//  private final PhotoService photoService;
//
//  @Autowired
//  public PhotoController(PhotoService photoService) {
//    this.photoService = photoService;
//  }
//
//  //TODO: реализовать
//  @GetMapping("/photos")
//  public List<PhotoJson> getPhotosForUser() {
//    return photoService.getAllUserPhotos();
//  }
//
//  //TODO: реализовать
//  @GetMapping("/friends/photos")
//  public List<PhotoJson> getAllFriendsPhotos() {
//    return photoService.getAllFriendsPhotos();
//  }
//
//  //TODO: реализовать
//  @PostMapping("/photos")
//  public PhotoJson addPhoto(@RequestBody PhotoJson photoJson) {
//    return photoService.addPhoto(photoJson);
//  }
//
//  //TODO: реализовать
//  @PatchMapping("/photos/{id}")
//  public PhotoJson editPhoto(@RequestBody PhotoJson photoJson) {
//    return photoService.editPhoto(photoJson);
//  }
//
//  //TODO: реализовать
//  @DeleteMapping("/photos")
//  public void deletePhoto(@RequestParam UUID photoId) {
//    photoService.deletePhoto(photoId);
//  }
//
//}
