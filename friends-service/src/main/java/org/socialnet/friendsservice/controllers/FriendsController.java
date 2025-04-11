package org.socialnet.friendsservice.controllers;

import lombok.RequiredArgsConstructor;
import org.socialnet.friendsservice.entities.Friend;
import org.socialnet.friendsservice.entities.others.FriendDTO;
import org.socialnet.friendsservice.services.FriendServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendsController {

    private final FriendServiceImpl friendService;

    @PostMapping("/send")
    public ResponseEntity<?> sendFriendshipRequest(@RequestBody FriendDTO friendship) {

        if (friendship.getUser1Id() == null || friendship.getUser2Id() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User1 or User2 are not found.");
        }

        Friend senderFriendship = friendService.sendFriendRequest(friendship.getUser1Id(), friendship.getUser2Id());
        if (senderFriendship == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Friendship request failed.");
        }
        return ResponseEntity.ok(senderFriendship);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptFriendshipRequest(@PathVariable long id) {
        if (friendService.exists(id)){
            Friend acceptedRequest = friendService.acceptFriendRequest(id);
            if (acceptedRequest != null) {
                return ResponseEntity.ok(acceptedRequest);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Friendship request failed.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Friendship request failed.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Friend>> getFriendRequests(@PathVariable Long id) {
        List<Friend> friends = friendService.getFriendRequests(id);

        if (friends.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(friends);
    }

    @GetMapping("/myfriends/{id}")
    public ResponseEntity<List<Friend>> getMyFriends(@PathVariable Long id) {
        List<Friend> friends = friendService.getUserFriends(id);
        if (friends.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(friends);
    }
}
