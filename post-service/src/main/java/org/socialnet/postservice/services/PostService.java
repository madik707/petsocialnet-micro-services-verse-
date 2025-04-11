package org.socialnet.postservice.services;

import org.socialnet.postservice.entities.DTOs.PostDTO;
import org.socialnet.postservice.entities.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post createPost(PostDTO post);
    Optional<Post> getPostById(Long id);
    List<Post> getAllPosts();
    List<Post> getPostsByUserId(Long userId);
    Post updatePost(PostDTO postDTO);
    Post deletePost(Long id);
    Boolean isPostExist(Long id);

}
