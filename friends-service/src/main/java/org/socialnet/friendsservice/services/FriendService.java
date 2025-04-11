package org.socialnet.friendsservice.services;

import org.socialnet.friendsservice.entities.Friend;

import java.util.List;

public interface FriendService {
    Friend sendFriendRequest(Long senderId, Long receiverId);
    Friend acceptFriendRequest(Long requestId);
    List<Friend> getUserFriends(Long userId);
    List<Friend> getFriendRequests(Long userId);
}
