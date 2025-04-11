package org.socialnet.friendsservice.repositories;

import org.socialnet.friendsservice.entities.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByUser1Id(Long user1);

    List<Friend> findByUser2Id(Long user2);

    Optional<Friend> findByUser1IdAndUser2Id(Long user1, Long user2);

    List<Friend> findByUser1IdOrUser2Id(Long userId, Long friendId);
    Boolean existsByUser1IdAndUser2Id(Long user1, Long user2);


}
