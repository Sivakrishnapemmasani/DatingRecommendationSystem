package com.estuate.datingapp.datingrecommendationengine.service;

import com.estuate.datingapp.datingrecommendationengine.dto.UserRequest;
import com.estuate.datingapp.datingrecommendationengine.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse registerUser(UserRequest userRequest);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(int id);
    UserResponse getUserByName(String name);
    void deleteUser(int id);
    UserResponse updateUser(int id, UserRequest userRequest);
    UserResponse updateUserByName(String name, UserRequest userRequest);
}
