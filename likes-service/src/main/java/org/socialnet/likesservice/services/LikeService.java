package org.socialnet.likesservice.services;


import org.socialnet.likesservice.entities.Like;
import org.socialnet.likesservice.entities.dtos.LikeDTO;

import java.util.List;
import java.util.Optional;

public interface LikeService {

    Like createLike(LikeDTO like);
    Optional<Like> getLikeById(Long id);
    Like deleteLike(LikeDTO like);
    Long countByPost(Long postId);
    List<Like> findByPostId(Long postId);
    Boolean existsByUserIdAndPostId(LikeDTO likeDTO);

}
