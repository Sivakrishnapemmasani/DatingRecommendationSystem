package com.estuate.datingapp.datingrecommendationengine.controller;

import com.estuate.datingapp.datingrecommendationengine.dto.UserResponse;
import com.estuate.datingapp.datingrecommendationengine.service.RecommendationService;
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
class RecommendationsControllerTest {

    @Mock
    private RecommendationService recommendationService;

    @InjectMocks
    private RecommendationsController recommendationsController;

    @Test
    void testGetRecommendations_Success() {
        List<UserResponse> mockResponse = List.of(
                new UserResponse(1, "Alice", "Female", 25, Set.of("Cricket", "Chess"))
        );

        when(recommendationService.getRecommendations("Bob")).thenReturn(mockResponse);

        ResponseEntity<List<UserResponse>> response = recommendationsController.getRecommendations("Bob");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void testGetTopMatches_Success() {
        List<UserResponse> mockResponse = List.of(
                new UserResponse(1, "Alice", "Female", 25, Set.of("Cricket", "Chess"))
        );

        when(recommendationService.getTopMatches("Bob", 1)).thenReturn(mockResponse);

        ResponseEntity<List<UserResponse>> response = recommendationsController.getTopMatches("Bob", 1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void testGetRecommendations_EmptyList() {
        when(recommendationService.getRecommendations("Bob")).thenReturn(List.of());

        ResponseEntity<List<UserResponse>> response = recommendationsController.getRecommendations("Bob");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody() != null && response.getBody().isEmpty());
    }

    @Test
    void testGetTopMatches_ZeroLimit() {
        when(recommendationService.getTopMatches("Bob", 0)).thenReturn(List.of());

        ResponseEntity<List<UserResponse>> response = recommendationsController.getTopMatches("Bob", 0);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody() != null && response.getBody().isEmpty());
    }
}