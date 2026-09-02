package com.smartroom.service.impl;

import com.smartroom.dto.request.UserRequest;
import com.smartroom.dto.response.UserResponse;
import com.smartroom.entity.User;
import com.smartroom.repository.RoomRepository;
import com.smartroom.repository.UserRepository;
import com.smartroom.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        userRepository.save(user);
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.getOne(id);
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        User user = userRepository.getOne(userId);
        modelMapper.map(userRequest, user);
        User updatedUser=userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponse.class);
    }

    @Override
    public void deleteUser(Long id) {
        User deletedUser=userRepository.getOne(id);
        userRepository.delete(deletedUser);

    }
}
