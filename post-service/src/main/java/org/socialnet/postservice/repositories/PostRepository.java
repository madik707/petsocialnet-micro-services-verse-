package org.socialnet.postservice.repositories;

import org.socialnet.postservice.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserid(Long userId);

    List<Post>findByContentContaining(String keyword);


}

