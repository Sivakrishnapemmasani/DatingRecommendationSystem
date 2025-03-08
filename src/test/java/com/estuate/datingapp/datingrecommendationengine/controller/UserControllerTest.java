package com.estuate.datingapp.datingrecommendationengine.controller;

import com.estuate.datingapp.datingrecommendationengine.dto.UserRequest;
import com.estuate.datingapp.datingrecommendationengine.dto.UserResponse;
import com.estuate.datingapp.datingrecommendationengine.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testRegisterUser_Success() {
        UserRequest request = new UserRequest("John Doe", "Male", 30, Set.of("Cricket"));
        UserResponse mockResponse = new UserResponse(1, "John Doe", "Male", 30, Set.of("Cricket"));

        when(userService.registerUser(request)).thenReturn(mockResponse);

        ResponseEntity<UserResponse> response = userController.registerUser(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void testGetUserById_Success() {
        UserResponse mockResponse = new UserResponse(1, "John Doe", "Male", 30, Set.of("Cricket"));

        when(userService.getUserById(1)).thenReturn(mockResponse);

        ResponseEntity<UserResponse> response = userController.getUserById(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void testGetUserByName_Success() {
        UserResponse mockResponse = new UserResponse(1, "John Doe", "Male", 30, Set.of("Cricket"));

        when(userService.getUserByName("John Doe")).thenReturn(mockResponse);

        ResponseEntity<UserResponse> response = userController.getUserByName("John Doe");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void testGetAllUsers_Success() {
        List<UserResponse> mockResponse = List.of(
                new UserResponse(1, "Alice", "Female", 25, Set.of("Cricket", "Chess"))
        );

        when(userService.getAllUsers()).thenReturn(mockResponse);

        ResponseEntity<List<UserResponse>> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void testDeleteUser_Success() {
        doNothing().when(userService).deleteUser(1);

        ResponseEntity<Void> response = userController.deleteUser(1);

        assertNotNull(response);
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void testUpdateUser_Success() {
        UserRequest request = new UserRequest("Updated John", "Male", 32, Set.of("Football"));
        UserResponse mockResponse = new UserResponse(1, "Updated John", "Male", 32, Set.of("Football"));

        when(userService.updateUser(1, request)).thenReturn(mockResponse);

        ResponseEntity<UserResponse> response = userController.updateUser(1, request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void testUpdateUserByName_Success() {
        UserRequest request = new UserRequest("Updated John", "Male", 32, Set.of("Football"));
        UserResponse mockResponse = new UserResponse(1, "Updated John", "Male", 32, Set.of("Football"));

        when(userService.updateUserByName("John Doe", request)).thenReturn(mockResponse);

        ResponseEntity<UserResponse> response = userController.updateUserByName("John Doe", request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
    }
}
