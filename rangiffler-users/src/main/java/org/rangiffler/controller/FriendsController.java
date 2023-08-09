package org.rangiffler.controller;


import org.rangiffler.model.UserJson;
import org.rangiffler.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FriendsController {

    private final UserDataService userService;

    @Autowired
    public FriendsController(UserDataService userService) {
        this.userService = userService;
    }

    @GetMapping("/friends")
    public List<UserJson> friends(@RequestParam String username) {
        return userService.friends(username);
    }

    @GetMapping("/invitations")
    public List<UserJson> invitations(@RequestParam String username) {
        return userService.invitations(username);
    }

    @PostMapping("/acceptInvitation")
    public UserJson acceptInvitation(@RequestParam String username,
                                           @RequestBody UserJson invitation) {
        return userService.acceptInvitation(username, invitation);
    }

    @PostMapping("/declineInvitation")
    public UserJson declineInvitation(@RequestParam String username,
                                            @RequestBody UserJson invitation) {
        return userService.declineInvitation(username, invitation);
    }

    @PostMapping("/addFriend")
    public UserJson addFriend(@RequestParam String username,
                              @RequestBody UserJson friend) {
        return userService.addFriend(username, friend);
    }

    @DeleteMapping("/removeFriend")
    public UserJson removeFriend(@RequestParam String username,
                                       @RequestParam String friendUsername) {
        return userService.removeFriend(username, friendUsername);
    }
}
