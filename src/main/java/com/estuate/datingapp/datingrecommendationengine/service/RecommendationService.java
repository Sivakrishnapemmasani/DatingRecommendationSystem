package com.estuate.datingapp.datingrecommendationengine.service;

import com.estuate.datingapp.datingrecommendationengine.dto.UserResponse;

import java.util.List;

public interface RecommendationService {

    List<UserResponse> getRecommendations(String username);
    List<UserResponse> getTopMatches(String username, int limit);
}
