package org.socialnet.commentservice.services;

import org.socialnet.commentservice.entities.Comment;
import org.socialnet.commentservice.entities.dtos.CommentDTO;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Comment createComment(CommentDTO comment);
    Optional<Comment> getCommentById(Long id);
    List<Comment> getCommentsByPost(Long postId);
    Comment updateComment(CommentDTO comment);
    void deleteComment(Long id);

}
