package org.rangiffler.service;

import jakarta.annotation.Nonnull;
import org.rangiffler.data.FriendsEntity;
import org.rangiffler.data.UserEntity;
import org.rangiffler.data.repository.UserRepository;
import org.rangiffler.model.FriendStatus;
import org.rangiffler.model.UserJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserDataService {

    private final UserRepository userRepository;

    @Autowired
    public UserDataService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public @Nonnull
    UserJson update(@Nonnull UserJson user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername());
        if (userEntity != null) {
            userEntity.setFirstname(user.getFirstName());
            userEntity.setLastname(user.getLastName());
            userEntity.setAvatar(user.getAvatar() != null ? user.getAvatar().getBytes(StandardCharsets.UTF_8) : null);
            UserEntity saved = userRepository.save(userEntity);

            return UserJson.fromEntity(saved);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
    }

    public @Nonnull
    UserJson getCurrentUserOrCreateIfAbsent(@Nonnull String username) {
        UserEntity userDataEntity = userRepository.findByUsername(username);
        if (userDataEntity == null) {
            userDataEntity = new UserEntity();
            userDataEntity.setUsername(username);
            return UserJson.fromEntity(userRepository.save(userDataEntity));
        } else {
            return UserJson.fromEntity(userDataEntity);
        }
    }

    public @Nonnull
    List<UserJson> allUsers(@Nonnull String username) {
        Map<UUID, UserJson> result = new HashMap<>();
        for (UserEntity user : userRepository.findByUsernameNot(username)) {
            List<FriendsEntity> sendInvites = user.getFriends();
            List<FriendsEntity> receivedInvites = user.getInvites();

            if (!sendInvites.isEmpty() || !receivedInvites.isEmpty()) {
                Optional<FriendsEntity> inviteToMe = sendInvites.stream()
                        .filter(i -> i.getFriend().getUsername().equals(username))
                        .findFirst();

                Optional<FriendsEntity> inviteFromMe = receivedInvites.stream()
                        .filter(i -> i.getUser().getUsername().equals(username))
                        .findFirst();

                if (inviteToMe.isPresent()) {
                    FriendsEntity invite = inviteToMe.get();
                    result.put(user.getId(), UserJson.fromEntity(user, invite.isPending()
                            ? FriendStatus.INVITATION_RECEIVED
                            : FriendStatus.FRIEND));
                }
                if (inviteFromMe.isPresent()) {
                    FriendsEntity invite = inviteFromMe.get();
                    result.put(user.getId(), UserJson.fromEntity(user, invite.isPending()
                            ? FriendStatus.INVITATION_SENT
                            : FriendStatus.FRIEND));
                }
            }
            if (!result.containsKey(user.getId())) {
                result.put(user.getId(), UserJson.fromEntity(user, FriendStatus.NOT_FRIEND));
            }
        }
        return new ArrayList<>(result.values());
    }

    public @Nonnull
    List<UserJson> friends(@Nonnull String username) {
        return userRepository.findByUsername(username)
                .getFriends()
                .stream()
                .map(fe -> UserJson.fromEntity(fe.getFriend(), fe.isPending()
                        ? FriendStatus.INVITATION_SENT
                        : FriendStatus.FRIEND))
                .toList();
    }

    public @Nonnull
    List<UserJson> invitations(@Nonnull String username) {
        return userRepository.findByUsername(username)
                .getInvites()
                .stream()
                .filter(FriendsEntity::isPending)
                .map(fe -> UserJson.fromEntity(fe.getUser(), FriendStatus.INVITATION_RECEIVED))
                .toList();
    }

    public @Nonnull
    UserJson addFriend(@Nonnull String username, @Nonnull UserJson friend) {
        UserEntity currentUser = userRepository.findByUsername(username);
        UserEntity friendEntity = userRepository.findByUsername(friend.getUsername());
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find user by username: " + username);
        }
        if (friendEntity == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find user by username: " + friend.getUsername());
        }

        currentUser.addFriends(true, friendEntity);
        userRepository.save(currentUser);
        return UserJson.fromEntity(friendEntity, FriendStatus.INVITATION_SENT);
    }

    public @Nonnull
    UserJson acceptInvitation(@Nonnull String username, @Nonnull UserJson invitation) {
        UserEntity currentUser = userRepository.findByUsername(username);
        UserEntity inviteUser = userRepository.findByUsername(invitation.getUsername());
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find user by username: " + username);
        }
        if (inviteUser == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find user by username: " + invitation.getUsername());
        }

        FriendsEntity invite = currentUser.getInvites()
                .stream()
                .filter(fe -> fe.getUser().equals(inviteUser))
                .findFirst()
                .orElseThrow();

        invite.setPending(false);
        currentUser.addFriends(false, inviteUser);
        userRepository.save(currentUser);

        return currentUser
                .getFriends()
                .stream()
                .filter(cu -> cu.getFriend().getUsername().equals(invitation.getUsername()))
                .map(fe -> UserJson.fromEntity(fe.getFriend(), fe.isPending()
                        ? FriendStatus.INVITATION_SENT
                        : FriendStatus.FRIEND))
                .findFirst()
                .orElseThrow();
    }


    public @Nonnull
    UserJson declineInvitation(@Nonnull String username, @Nonnull UserJson invitation) {
        UserEntity currentUser = userRepository.findByUsername(username);
        UserEntity friendToDecline = userRepository.findByUsername(invitation.getUsername());
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find user by username: " + username);
        }
        if (friendToDecline == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find user by username: " + invitation.getUsername());
        }

        currentUser.removeInvites(friendToDecline);
        friendToDecline.removeFriends(currentUser);

        userRepository.save(currentUser);
        userRepository.save(friendToDecline);

        return UserJson.fromEntity(friendToDecline, FriendStatus.NOT_FRIEND);
    }

    public @Nonnull
    UserJson removeFriend(@Nonnull String username, @Nonnull String friendUsername) {
        UserEntity currentUser = userRepository.findByUsername(username);
        UserEntity friendToRemove = userRepository.findByUsername(friendUsername);
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find user by username: " + username);
        }
        if (friendToRemove == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find user by username: " + friendUsername);
        }

        currentUser.removeFriends(friendToRemove);
        currentUser.removeInvites(friendToRemove);
        userRepository.save(currentUser);
        return UserJson.fromEntity(friendToRemove, FriendStatus.NOT_FRIEND);
    }
}
