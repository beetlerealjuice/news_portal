package com.example.news_portal.mapper;

import com.example.news_portal.model.User;
import com.example.news_portal.web.model.request.UpsertUserRequest;
import com.example.news_portal.web.model.response.UserListResponse;
import com.example.news_portal.web.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {NewsMapper.class})
public interface UserMapper {

    User requestToUser(UpsertUserRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UpsertUserRequest request);

    UserResponse userToResponse(User user);

    default UserListResponse userListResponseToUserResponseList(List<User> users) {
        UserListResponse response = new UserListResponse();

        response.setUsers(users.stream()
                .map(this::userToResponse)
                .toList());
        return response;
    }
}
