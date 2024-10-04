package com.example.news_portal.web.controller;

import com.example.news_portal.aop.Loggable;
import com.example.news_portal.mapper.UserMapper;
import com.example.news_portal.model.Role;
import com.example.news_portal.model.RoleType;
import com.example.news_portal.model.User;
import com.example.news_portal.services.UserService;
import com.example.news_portal.web.model.request.UpsertUserRequest;
import com.example.news_portal.web.model.response.UserListResponse;
import com.example.news_portal.web.model.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService databaseUserService;

    private final UserMapper userMapper;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserListResponse> findAll(Pageable pageable) {
        Page<User> userPage = databaseUserService.findAll(pageable);
        UserListResponse response = userMapper.userListResponseToUserResponseList(userPage.getContent());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Loggable
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userMapper.userToResponse(
                databaseUserService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UpsertUserRequest request,
                                               @RequestParam RoleType roleType) {
        User newUser = databaseUserService.save(userMapper.requestToUser(request), Role.from(roleType));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(newUser));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Loggable
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long userId,
                                                   @AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody @Valid UpsertUserRequest request) {
        User updatedUser = databaseUserService.update(userMapper.requestToUser(userId, request));
        System.out.println("Fuck");
        return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Loggable
    public ResponseEntity<Void> deleteUser(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        databaseUserService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
