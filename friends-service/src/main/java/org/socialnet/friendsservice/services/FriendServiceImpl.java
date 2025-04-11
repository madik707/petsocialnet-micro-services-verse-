package org.socialnet.friendsservice.services;

import lombok.RequiredArgsConstructor;
import org.socialnet.friendsservice.entities.Friend;
import org.socialnet.friendsservice.entities.others.FriendshipStatus;
import org.socialnet.friendsservice.repositories.FriendRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;

    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;


    @Override
    public Friend sendFriendRequest(Long senderId, Long receiverId) {

        if(isUserExist(senderId) || isUserExist(receiverId)) {
            if (!friendRepository.existsByUser1IdAndUser2Id(senderId, receiverId)) {
                Friend friend = new Friend();
                friend.setUser1Id(senderId);
                friend.setUser2Id(receiverId);
                friend.setStatus(FriendshipStatus.PENDING);
                friendRepository.save(friend);
                return friend;
            }
        }
        return null;
    }

    @Override
    public Friend acceptFriendRequest(Long requestId) {

        if (friendRepository.existsById(requestId)) {

            Optional<Friend> friend = friendRepository.findById(requestId);
            if (friend.isPresent()) {
                friend.get().setStatus(FriendshipStatus.ACCEPTED);
                friendRepository.save(friend.get());
                return friend.get();
            }
        }

        return null;
    }

    @Override
    public List<Friend> getUserFriends(Long userId) {
        if (isUserExist(userId)) {
            List<Friend> friendsRequests = friendRepository.findByUser1IdOrUser2Id(userId, userId);
            List<Friend> friends = new ArrayList<>();
            for (Friend friend : friendsRequests) {
                if (friend.getStatus() == FriendshipStatus.ACCEPTED) {
                    friends.add(friend);
                }
            }
            return friends;
        }
        return null;
    }

    @Override
    public List<Friend> getFriendRequests(Long userId) {
        if (isUserExist(userId)) {
            return friendRepository.findByUser1IdOrUser2Id(userId, userId);
        }
        return null;
    }

    public boolean exists(Long id){
        return friendRepository.existsById(id);
    }

    private boolean isUserExist(Long userId) {
        String url = userServiceUrl + "users/exists/" + userId;
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);

        return response.getBody();
    }
}
