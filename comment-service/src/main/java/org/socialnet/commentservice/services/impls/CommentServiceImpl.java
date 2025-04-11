package org.socialnet.commentservice.services.impls;

import lombok.RequiredArgsConstructor;
import org.socialnet.commentservice.entities.Comment;
import org.socialnet.commentservice.entities.dtos.CommentDTO;
import org.socialnet.commentservice.repositories.CommentRepository;
import org.socialnet.commentservice.services.CommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;

    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    @Value("${post.service.url}")
    private String postServiceUrl;

    @Override
    public Comment createComment(CommentDTO comment) {
        String usersUrl = userServiceUrl + "/users/exists/" + comment.getUserId();
        ResponseEntity<Boolean> usersResponse = restTemplate.getForEntity(usersUrl, Boolean.class);
        if (Boolean.TRUE.equals(usersResponse.getBody())) {
            String postsUrl = postServiceUrl + "/posts/exists/" + comment.getPostId();
            ResponseEntity<Boolean> postsResponse = restTemplate.getForEntity(postsUrl, Boolean.class);
            if (Boolean.TRUE.equals(postsResponse.getBody())) {
                Comment commentToPost = new Comment();
                commentToPost.setUserId(comment.getUserId());
                commentToPost.setPostId(comment.getPostId());
                commentToPost.setContent(comment.getContent());
                return commentRepository.save(commentToPost);
            }
        }
        return null;
    }

    @Override
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> getCommentsByPost(Long postId) {
        String postsUrl = postServiceUrl + "/posts/exists/" + postId;
        ResponseEntity<Boolean> postsResponse = restTemplate.getForEntity(postsUrl, Boolean.class);

        if (Boolean.TRUE.equals(postsResponse.getBody())) {
            return commentRepository.findByPostId(postId);
        }

        return null;

    }

    @Override
    public Comment updateComment(CommentDTO commentDTO) {
        return commentRepository.findById(commentDTO.getId())
                .map(comment -> {
                    comment.setContent(commentDTO.getContent());
                    return commentRepository.save(comment);
                })
                .orElseThrow(() -> new RuntimeException("Comment not found with id " + commentDTO.getId()));
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

}
