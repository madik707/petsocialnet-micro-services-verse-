package org.socialnet.commentservice.controllers;

import lombok.RequiredArgsConstructor;
import org.socialnet.commentservice.entities.Comment;
import org.socialnet.commentservice.entities.dtos.CommentDTO;
import org.socialnet.commentservice.services.impls.CommentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO commentDTO) {

        Comment comment = commentService.createComment(commentDTO);
        if (comment != null) {
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@RequestParam Long postId) {
        List<Comment> comments = commentService.getCommentsByPost(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Comment> updateComment(@RequestBody CommentDTO comment) {
        commentService.updateComment(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteComment(@RequestBody CommentDTO commentDTO) {
        commentService.deleteComment(commentDTO.getId());
    }
}
