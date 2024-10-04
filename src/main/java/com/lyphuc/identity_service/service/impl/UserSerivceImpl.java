package com.lyphuc.identity_service.service.impl;

import com.lyphuc.identity_service.dto.request.UserCreationRequest;
import com.lyphuc.identity_service.dto.request.UserUpdateRequest;
import com.lyphuc.identity_service.dto.response.UserResponse;
import com.lyphuc.identity_service.entity.User;
import com.lyphuc.identity_service.enums.Role;
import com.lyphuc.identity_service.exception.AppException;
import com.lyphuc.identity_service.exception.ErrorCode;
import com.lyphuc.identity_service.mapper.UserMapper;
import com.lyphuc.identity_service.repository.RoleRepository;
import com.lyphuc.identity_service.repository.UserRepository;
import com.lyphuc.identity_service.service.UserSerivce;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserSerivceImpl implements UserSerivce {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        //user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(String userId,UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Cannot find user"));

        userMapper.updateUser(user,request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    //Kiểm tra quyền trước khi chạy hàm

    @Override
    @PreAuthorize("hasRole('ADMIN')")//To Authorize Role
//    @PreAuthorize("hasAuthority('CREATE_DATA')")//To Authorize Permission OR Role: ROLE_ADMIN
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }
    //PostAuthorize chạy method trước, xong mới check đúng quyền không, nếu không thì không trả về kết quả hàm
    //returnObject.username == authentication.name khi user đã đăng nhập trùng với username trên url thì mới trả về
    @PostAuthorize("returnObject.username == authentication.name")
    @Override
    public UserResponse getUser(String userId) {
        return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Cannot find user")));
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }


}
