package org.socialnet.likesservice.controller;

import lombok.RequiredArgsConstructor;
import org.socialnet.likesservice.entities.Like;
import org.socialnet.likesservice.entities.dtos.LikeDTO;
import org.socialnet.likesservice.services.impls.LikeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeServiceImpl likeService;


    @PostMapping
    public ResponseEntity<?> like(@RequestBody final LikeDTO likeDTO) {

        if (likeService.existsByUserIdAndPostId(likeDTO)){
            likeService.deleteLike(likeDTO);
            return ResponseEntity.ok("Like successfully deleted");
        }

        Like like = likeService.createLike(likeDTO);
        return ResponseEntity.ok(like);
    }

    @GetMapping
    public ResponseEntity<List<Like>> getLikesByPost(@RequestParam Long postId) {
        List<Like> likesByPost = likeService.findByPostId(postId);
        if (likesByPost == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(likesByPost, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLike(@RequestBody LikeDTO likeDTO) {
        Like like = likeService.deleteLike(likeDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countLikes(@RequestParam Long postId) {

        Long likesCount = likeService.countByPost(postId);
        return new ResponseEntity<>(likesCount, HttpStatus.OK);

    }
}