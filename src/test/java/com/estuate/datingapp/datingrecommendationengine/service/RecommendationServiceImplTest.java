package com.estuate.datingapp.datingrecommendationengine.service;

import com.estuate.datingapp.datingrecommendationengine.dto.UserResponse;
import com.estuate.datingapp.datingrecommendationengine.entity.InterestEntity;
import com.estuate.datingapp.datingrecommendationengine.entity.UserEntity;
import com.estuate.datingapp.datingrecommendationengine.exception.UserNotFoundException;
import com.estuate.datingapp.datingrecommendationengine.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    private UserEntity currentUser;
    private UserEntity match1;
    private UserEntity match2;
    private UserEntity match3;

    @BeforeEach
    void setUp() {
        currentUser = new UserEntity();
        currentUser.setId(1);
        currentUser.setName("Bob");
        currentUser.setGender("Male");
        currentUser.setAge(27);
        currentUser.setInterests(Set.of(new InterestEntity("Cricket"), new InterestEntity("Movies")));

        match1 = new UserEntity();
        match1.setId(2);
        match1.setName("Alice");
        match1.setGender("Female");
        match1.setAge(25);
        match1.setInterests(Set.of(new InterestEntity("Cricket"), new InterestEntity("Chess")));

        match2 = new UserEntity();
        match2.setId(3);
        match2.setName("Diana");
        match2.setGender("Female");
        match2.setAge(24);
        match2.setInterests(Set.of(new InterestEntity("Tennis"), new InterestEntity("Football")));

        match3 = new UserEntity();
        match3.setId(4);
        match3.setName("Emily");
        match3.setGender("Female");
        match3.setAge(32);
        match3.setInterests(Set.of(new InterestEntity("Cricket"), new InterestEntity("Movies"), new InterestEntity("Badminton")));
    }

    @Test
    void testGetRecommendations_Success() {
        when(userRepository.findByName("Bob")).thenReturn(Optional.of(currentUser));
        when(userRepository.findAll()).thenReturn(List.of(currentUser, match1, match2, match3));

        List<UserResponse> recommendations = recommendationService.getRecommendations("Bob");

        assertNotNull(recommendations);
        assertFalse(recommendations.isEmpty());
        assertEquals(3, recommendations.size()); // All female users should be recommended
    }

    @Test
    void testGetRecommendations_UserNotFound() {
        when(userRepository.findByName("UnknownUser")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> recommendationService.getRecommendations("UnknownUser"));
    }

    @Test
    void testGetTopMatches_Success() {
        when(userRepository.findByName("Bob")).thenReturn(Optional.of(currentUser));
        when(userRepository.findAll()).thenReturn(List.of(currentUser, match1, match2, match3));

        List<UserResponse> topMatches = recommendationService.getTopMatches("Bob", 2);

        assertNotNull(topMatches);
        assertEquals(2, topMatches.size()); // Only top 2 matches should be returned
    }

    @Test
    void testGetTopMatches_UserNotFound() {
        when(userRepository.findByName("UnknownUser")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> recommendationService.getTopMatches("UnknownUser", 2));
    }

    @Test
    void testGetRecommendations_NoMatchesFound() {
        when(userRepository.findByName("Bob")).thenReturn(Optional.of(currentUser));
        when(userRepository.findAll()).thenReturn(Collections.singletonList(currentUser));

        List<UserResponse> recommendations = recommendationService.getRecommendations("Bob");

        assertNotNull(recommendations);
        assertTrue(recommendations.isEmpty()); // No matches should be found
    }

    @Test
    void testGetTopMatches_LimitExceedsAvailableMatches() {
        when(userRepository.findByName("Bob")).thenReturn(Optional.of(currentUser));
        when(userRepository.findAll()).thenReturn(List.of(currentUser, match1));

        List<UserResponse> topMatches = recommendationService.getTopMatches("Bob", 5);

        assertNotNull(topMatches);
        assertEquals(1, topMatches.size()); // Only 1 match available, should return that
    }

    @Test
    void testGetRecommendations_EmptyInterestList() {
        currentUser.setInterests(Collections.emptySet());
        when(userRepository.findByName("Bob")).thenReturn(Optional.of(currentUser));
        when(userRepository.findAll()).thenReturn(List.of(currentUser, match1, match2, match3));

        List<UserResponse> recommendations = recommendationService.getRecommendations("Bob");

        assertNotNull(recommendations);
        assertEquals(3, recommendations.size()); // Should still match by gender & age
    }
}