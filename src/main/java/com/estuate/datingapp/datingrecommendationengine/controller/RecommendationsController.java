package com.estuate.datingapp.datingrecommendationengine.controller;

import com.estuate.datingapp.datingrecommendationengine.dto.UserResponse;
import com.estuate.datingapp.datingrecommendationengine.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationsController {

    private final RecommendationService recommendationService;

    public RecommendationsController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @GetMapping("/username/{username}")
    public ResponseEntity<List<UserResponse>> getRecommendations(@PathVariable String username) {
        List<UserResponse> recommendations = recommendationService.getRecommendations(username);
        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/username/{username}/top/{limit}")
    public ResponseEntity<List<UserResponse>> getTopMatches(@PathVariable String username, @PathVariable int limit) {
        List<UserResponse> recommendations = recommendationService.getTopMatches(username, limit);
        return ResponseEntity.ok(recommendations);
    }
}