package com.example.news_portal.web.controller;

import com.example.news_portal.aop.Loggable;
import com.example.news_portal.mapper.CommentMapper;
import com.example.news_portal.model.Comment;
import com.example.news_portal.services.CommentService;
import com.example.news_portal.services.DatabaseUserService;
import com.example.news_portal.web.model.request.UpsertCommentRequest;
import com.example.news_portal.web.model.response.CommentListResponse;
import com.example.news_portal.web.model.response.CommentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService databaseCommentService;

    private final CommentMapper commentMapper;

    private final DatabaseUserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentListResponse> findAll(Pageable pageable) {

        CommentListResponse response = commentMapper
                .commentListToCommentListResponse(
                        databaseCommentService
                                .findAll(pageable).getContent());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                commentMapper.commentToResponse(databaseCommentService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> createComment(@RequestBody @Valid UpsertCommentRequest request,
                                                         @AuthenticationPrincipal UserDetails userDetails) {

        request.setUserId(userService.findByUsername(userDetails.getUsername()).getId());

        Comment newComment = databaseCommentService.save(commentMapper.requestToComment(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentMapper.commentToResponse(newComment));
    }

    @PutMapping("/{id}")
    @Loggable
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable("id") Long commentId,
                                                         @AuthenticationPrincipal UserDetails userDetails,
                                                         @RequestBody @Valid UpsertCommentRequest request) {

        request.setUserId(userService.findByUsername(userDetails.getUsername()).getId());

        Comment updatedComment = databaseCommentService.update(commentMapper.requestToComment(commentId, request));

        return ResponseEntity.ok(commentMapper.commentToResponse(updatedComment));
    }

    @DeleteMapping("/{id}")
    @Loggable
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        databaseCommentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
