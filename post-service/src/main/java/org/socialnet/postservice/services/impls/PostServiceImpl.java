package org.socialnet.postservice.services.impls;

import lombok.RequiredArgsConstructor;
import org.socialnet.postservice.entities.DTOs.PostDTO;
import org.socialnet.postservice.entities.Post;
import org.socialnet.postservice.repositories.PostRepository;
import org.socialnet.postservice.services.PostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;


    @Override
    public Post createPost(PostDTO postDTO) {

        if(isUserExist(postDTO.getUserId())) {
            Post post = new Post();
            post.setUserid(postDTO.getUserId());
            post.setContent(postDTO.getContent());

            return postRepository.save(post);
        }
        return null;
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getPostsByUserId(Long userId) {
        if (isUserExist(userId)) {
            return postRepository.findByUserid(userId);
        }
        return null;
    }

    public boolean isUserExist(Long userId) {
        String url = userServiceUrl + "/users/exists/" + userId;
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);

        return Boolean.TRUE.equals(response.getBody());
    }

    @Override
    public Post updatePost(PostDTO postDTO) {
        return postRepository.findById(postDTO.getId())
                .map(post -> {
                    post.setContent(postDTO.getContent());
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new RuntimeException("Post not found with id " + postDTO.getId()));
    }

    @Override
    public Post deletePost(Long id) {
        Optional<Post> post = getPostById(id);
        if (post.isPresent()) {
            postRepository.deleteById(id);
            return post.get();
        }
        return null;
    }

    @Override
    public Boolean isPostExist(Long id) {
        return postRepository.existsById(id);
    }
}

