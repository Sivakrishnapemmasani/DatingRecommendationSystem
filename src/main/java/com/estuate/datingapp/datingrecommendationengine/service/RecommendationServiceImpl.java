package com.estuate.datingapp.datingrecommendationengine.service;

import com.estuate.datingapp.datingrecommendationengine.dto.UserResponse;
import com.estuate.datingapp.datingrecommendationengine.entity.InterestEntity;
import com.estuate.datingapp.datingrecommendationengine.entity.UserEntity;
import com.estuate.datingapp.datingrecommendationengine.exception.UserNotFoundException;
import com.estuate.datingapp.datingrecommendationengine.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final UserRepository userRepository;

    public RecommendationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getRecommendations(String username) {
        UserEntity currentUser = userRepository.findByName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with name: " + username));

        Set<String> userInterests = currentUser.getInterests().stream()
                .map(InterestEntity::getName)
                .collect(Collectors.toSet());

        return userRepository.findAll().stream()
                .filter(user -> !user.getName().equalsIgnoreCase(username)) // Exclude current user
                .filter(user -> !user.getGender().equalsIgnoreCase(currentUser.getGender())) // Gender Rule: Opposite gender
                .sorted(Comparator.comparingInt(user -> Math.abs(user.getAge() - currentUser.getAge()))) // Age Rule: Closest age first
                .sorted((user1, user2) -> {
                    // Interest Rule: If age is the same, sort by highest shared interests
                    if (Math.abs(user1.getAge() - currentUser.getAge()) == Math.abs(user2.getAge() - currentUser.getAge())) {
                        long sharedInterests1 = user1.getInterests().stream()
                                .map(InterestEntity::getName)
                                .filter(userInterests::contains).count();

                        long sharedInterests2 = user2.getInterests().stream()
                                .map(InterestEntity::getName)
                                .filter(userInterests::contains).count();

                        return Long.compare(sharedInterests2, sharedInterests1); // Higher interest match first
                    }
                    return 0; // Maintain previous age-based sorting
                })
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getGender(),
                        user.getAge(),
                        user.getInterests().stream().map(InterestEntity::getName).collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
    }

    public List<UserResponse> getTopMatches(String username, int limit) {
        UserEntity currentUser = userRepository.findByName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with name: " + username));

        Set<String> userInterests = currentUser.getInterests().stream()
                .map(InterestEntity::getName)
                .collect(Collectors.toSet());

        return userRepository.findAll().stream()
                .filter(user -> !user.getName().equalsIgnoreCase(username)) // Exclude current user
                .filter(user -> !user.getGender().equalsIgnoreCase(currentUser.getGender())) // Opposite gender rule
                .sorted(Comparator.comparingInt(user -> Math.abs(user.getAge() - currentUser.getAge()))) // Age priority
                .sorted((user1, user2) -> {
                    // Interest priority (if ages are the same)
                    long sharedInterests1 = user1.getInterests().stream()
                            .map(InterestEntity::getName)
                            .filter(userInterests::contains).count();

                    long sharedInterests2 = user2.getInterests().stream()
                            .map(InterestEntity::getName)
                            .filter(userInterests::contains).count();

                    return Long.compare(sharedInterests2, sharedInterests1); // More shared interests first
                })
                .limit(limit) // Limit to top N matches
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getGender(),
                        user.getAge(),
                        user.getInterests().stream().map(InterestEntity::getName).collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
    }
}
