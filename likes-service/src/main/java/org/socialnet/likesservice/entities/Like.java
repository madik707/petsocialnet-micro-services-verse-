package org.socialnet.likesservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Table(name = "likes")
@ToString
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    @JsonBackReference
    private Long userId;


    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}

