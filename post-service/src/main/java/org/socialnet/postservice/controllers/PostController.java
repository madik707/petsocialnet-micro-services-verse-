package org.socialnet.postservice.controllers;

import lombok.RequiredArgsConstructor;
import org.socialnet.postservice.entities.DTOs.PostDTO;
import org.socialnet.postservice.entities.Post;
import org.socialnet.postservice.services.impls.PostServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostServiceImpl postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDTO post) {
        Post createdPost = postService.createPost(post);
        if (createdPost == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getUserPosts(@RequestParam Long userId) {

        List<Post> userPosts = postService.getPostsByUserId(userId);

        return new ResponseEntity<>(userPosts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        return post.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public ResponseEntity<Post> updatePost(@RequestBody PostDTO post) {
        Post updatedPost = postService.updatePost(post);
        if (updatedPost == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Post> deletePost(@RequestBody PostDTO post) {
        if (postService.getPostById(post.getId()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Post deletedPost = postService.deletePost(post.getId());
        return new ResponseEntity<>(deletedPost, HttpStatus.OK);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> isPostExists(@PathVariable Long id) {
        if (postService.isPostExist(id)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }
}
