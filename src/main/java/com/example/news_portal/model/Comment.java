package com.example.news_portal.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @CreationTimestamp
    private Instant createAt;

    @UpdateTimestamp
    private Instant updateAt;

    private String createdAt;

    private String updatedAt;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String commentOwner;

}
