package com.estuate.datingapp.datingrecommendationengine.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.estuate.datingapp.datingrecommendationengine.dto.UserRequest;
import com.estuate.datingapp.datingrecommendationengine.dto.UserResponse;
import com.estuate.datingapp.datingrecommendationengine.entity.InterestEntity;
import com.estuate.datingapp.datingrecommendationengine.entity.UserEntity;
import com.estuate.datingapp.datingrecommendationengine.exception.UserNotFoundException;
import com.estuate.datingapp.datingrecommendationengine.repository.InterestRepository;
import com.estuate.datingapp.datingrecommendationengine.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private UserEntity mockUser;

    @Mock
    private InterestRepository interestRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity user;
    private UserRequest userRequest;
    private Set<InterestEntity> interests;

    @BeforeEach
    void setUp() {
        interests = Set.of(new InterestEntity("Cricket"), new InterestEntity("Movies"));

        user = new UserEntity();
        user.setId(1);
        user.setName("John Doe");
        user.setGender("Male");
        user.setAge(25);
        user.setInterests(interests);

        userRequest = new UserRequest();
        userRequest.setName("Updated John");
        userRequest.setGender("Male");
        userRequest.setAge(26);
        userRequest.setInterests(Set.of("Cricket", "Football"));
    }

    @Test
    void testRegisterUser() {
        when(modelMapper.map(any(UserRequest.class), eq(UserEntity.class))).thenReturn(user);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(interestRepository.findByName(anyString())).thenReturn(Optional.of(new InterestEntity("Cricket")));

        UserResponse response = userService.registerUser(userRequest);

        assertNotNull(response);
        assertEquals("John Doe", response.getName());
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserResponse response = userService.getUserById(1);

        assertNotNull(response);
        assertEquals("John Doe", response.getName());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(99));
    }

    @Test
    void testUpdateUser_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        UserResponse response = userService.updateUser(1, userRequest);

        assertNotNull(response);
        assertEquals("Updated John", response.getName());
    }

    @Test
    void testUpdateUser_NotFound() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(99, userRequest));
    }

   

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponse> users = userService.getAllUsers();

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
    }
}
