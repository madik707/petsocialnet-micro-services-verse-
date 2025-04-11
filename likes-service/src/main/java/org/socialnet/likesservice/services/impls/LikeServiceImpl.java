package org.socialnet.likesservice.services.impls;

import lombok.RequiredArgsConstructor;
import org.socialnet.likesservice.entities.Like;
import org.socialnet.likesservice.entities.dtos.LikeDTO;
import org.socialnet.likesservice.repos.LikeRepository;
import org.socialnet.likesservice.services.LikeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    @Value("${post.service.url}")
    private String postServiceUrl;



    @Override
    public Like createLike(LikeDTO like) {
        if (existsByUserAndPost(like)) {
            Like likeEntity = new Like();
            likeEntity.setUserId(like.getUserId());
            likeEntity.setPostId(like.getPostId());
            return likeRepository.save(likeEntity);
        }
        return null;
    }

    @Override
    public Optional<Like> getLikeById(Long id) {
        return likeRepository.findById(id);
    }

    @Override
    public Like deleteLike(LikeDTO likeDTO) {
        if (existsByUserAndPost(likeDTO)){
            Optional<Like> likeToDelete = likeRepository.findByUserIdAndPostId(likeDTO.getUserId(), likeDTO.getPostId());
            likeToDelete.ifPresent(likeRepository::delete);
        }
        return null;
    }



    @Override
    public Long countByPost(Long postId) {
        String url = postServiceUrl + "/posts/exists/" + postId;
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        if (Boolean.TRUE.equals(response.getBody())) {
            return likeRepository.countByPostId(postId);
        }
        return null;
    }

    @Override
    public List<Like> findByPostId(Long postId) {
        String url = postServiceUrl + "/posts/exists/" + postId;
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        if (Boolean.TRUE.equals(response.getBody())) {
            return likeRepository.findByPostId(postId);
        }
        return null;
    }


    public boolean existsByUserAndPost(LikeDTO likeDTO) {
        String url = userServiceUrl + "/users/exists/" + likeDTO.getUserId();
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        if (Boolean.TRUE.equals(response.getBody())) {
            String postsUrl = postServiceUrl + "/posts/exists/" + likeDTO.getPostId();
            ResponseEntity<Boolean> postsResponse = restTemplate.getForEntity(postsUrl, Boolean.class);
            return Boolean.TRUE.equals(postsResponse.getBody());
        }
        return false;
    }

    @Override
    public Boolean existsByUserIdAndPostId(LikeDTO likeDTO) {
        return likeRepository.existsByUserIdAndPostId(likeDTO.getUserId(), likeDTO.getPostId());
    }
}