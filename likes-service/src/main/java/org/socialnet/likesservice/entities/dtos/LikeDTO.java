package org.socialnet.likesservice.entities.dtos;

import lombok.Getter;

@Getter
public class LikeDTO {
    private Long id;
    private Long userId;
    private Long postId;
}
