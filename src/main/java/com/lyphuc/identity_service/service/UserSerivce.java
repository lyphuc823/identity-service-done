package com.lyphuc.identity_service.service;

import com.lyphuc.identity_service.dto.request.UserCreationRequest;
import com.lyphuc.identity_service.dto.request.UserUpdateRequest;
import com.lyphuc.identity_service.dto.response.UserResponse;

import java.util.List;

public interface UserSerivce {
    UserResponse createUser(UserCreationRequest request);
    UserResponse updateUser(String userId,UserUpdateRequest request);
    void deleteUser(String userId);
    List<UserResponse> getUsers();
    UserResponse getUser(String userId);
}
