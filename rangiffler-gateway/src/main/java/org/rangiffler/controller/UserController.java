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
    public List<UserJson> friends(@AuthenticationPrincipal Jwt principal) {
        String username = principal.getClaim("sub");
        return userApiService.getFriends(username);
    }

    @GetMapping("invitations")
    public List<UserJson> getInvitations(@AuthenticationPrincipal Jwt principal) {
        String username = principal.getClaim("sub");
        return userApiService.getInvitations(username);
    }

    @PostMapping("users/invite/")
    public UserJson sendInvitation(@AuthenticationPrincipal Jwt principal,@Validated @RequestBody UserJson friend) {
        String username = principal.getClaim("sub");
        return userApiService.sendInvitation(username, friend);
    }

    //TODO: реализовать
    @PostMapping("friends/remove")
    public UserJson removeFriendFromUser(@AuthenticationPrincipal Jwt principal, @Validated @RequestBody UserJson friend) {
        String username = principal.getClaim("sub");
        return userApiService.removeUserFromFriends(username, friend);
    }

    @PostMapping("friends/submit")
    public UserJson submitFriend(@AuthenticationPrincipal Jwt principal, @Validated @RequestBody UserJson friend) {
        String username = principal.getClaim("sub");
        return userApiService.acceptInvitation(username, friend);
    }

    //TODO: реализовать
    @PostMapping("friends/decline")
    public UserJson declineFriend(@RequestBody UserJson friend) {
        return userApiService.declineInvitation(friend);
    }

}
