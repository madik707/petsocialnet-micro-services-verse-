package org.socialnet.postservice.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    private Long id;
    private Long userId;
    private String content;

}
