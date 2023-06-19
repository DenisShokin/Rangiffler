package org.rangiffler.controller;

import org.rangiffler.model.UserJson;
import org.rangiffler.service.api.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserApiService userApiService;

    @Autowired
    public UserController(UserApiService userApiService) {
        this.userApiService = userApiService;
    }

    @GetMapping("/users")
    public List<UserJson> getAllUsers(@AuthenticationPrincipal Jwt principal) {
        String username = principal.getClaim("sub");
        return userApiService.getAllUsers(username);
    }

    @GetMapping("/currentUser")
    public UserJson getCurrentUser(@AuthenticationPrincipal Jwt principal) {
        String username = principal.getClaim("sub");
        return userApiService.getCurrentUser(username);
    }

    @PatchMapping("/currentUser")
    public UserJson updateCurrentUser(@AuthenticationPrincipal Jwt principal,
                                      @Validated @RequestBody UserJson user) {
        String username = principal.getClaim("sub");
        user.setUsername(username);
        return userApiService.updateCurrentUser(user);
    }

    @GetMapping("/friends")
    public List<UserJson> getFriendsByUserId() {
        return userApiService.getFriends();
    }

    @GetMapping("invitations")
    public List<UserJson> getInvitations() {
        return userApiService.getInvitations();
    }

    @PostMapping("users/invite/")
    public UserJson sendInvitation(@RequestBody UserJson user) {
        return userApiService.sendInvitation(user);
    }

    @PostMapping("friends/remove")
    public UserJson removeFriendFromUser(@RequestBody UserJson friend) {
        return userApiService.removeUserFromFriends(friend);
    }

    @PostMapping("friends/submit")
    public UserJson submitFriend(@RequestBody UserJson friend) {
        return userApiService.acceptInvitation(friend);
    }

    @PostMapping("friends/decline")
    public UserJson declineFriend(@RequestBody UserJson friend) {
        return userApiService.declineInvitation(friend);
    }

}
