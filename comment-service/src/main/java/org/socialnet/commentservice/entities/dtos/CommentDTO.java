package org.socialnet.commentservice.entities.dtos;

import lombok.Getter;

@Getter
public class CommentDTO {

    private Long id;
    private Long userId;
    private Long postId;
    private String content;

}
