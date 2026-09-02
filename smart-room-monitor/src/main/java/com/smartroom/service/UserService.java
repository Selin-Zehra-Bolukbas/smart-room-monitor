package com.smartroom.service;


import com.smartroom.dto.request.UserRequest;
import com.smartroom.dto.response.UserResponse;
import com.smartroom.entity.User;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(Long id, UserRequest userRequest);
    void deleteUser(Long id);

}