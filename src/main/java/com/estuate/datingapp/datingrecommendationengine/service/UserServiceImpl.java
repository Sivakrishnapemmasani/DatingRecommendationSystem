package com.estuate.datingapp.datingrecommendationengine.service;

import com.estuate.datingapp.datingrecommendationengine.dto.UserRequest;
import com.estuate.datingapp.datingrecommendationengine.dto.UserResponse;
import com.estuate.datingapp.datingrecommendationengine.entity.InterestEntity;
import com.estuate.datingapp.datingrecommendationengine.entity.UserEntity;
import com.estuate.datingapp.datingrecommendationengine.exception.UserNotFoundException;
import com.estuate.datingapp.datingrecommendationengine.repository.InterestRepository;
import com.estuate.datingapp.datingrecommendationengine.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, InterestRepository interestRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.interestRepository = interestRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UserResponse registerUser(UserRequest userRequest) {
        Set<InterestEntity> interests = userRequest.getInterests().stream()
                .map(interestName -> interestRepository.findByName(interestName)
                        .orElseGet(() -> {
                            InterestEntity newInterest = new InterestEntity(interestName);
                            return interestRepository.save(newInterest); // Save new interest
                        }))
                .collect(Collectors.toSet());

        UserEntity userEntity = modelMapper.map(userRequest, UserEntity.class);
        userEntity.setInterests(interests);
        UserEntity savedUser = userRepository.save(userEntity);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getGender(),
                savedUser.getAge(),
                savedUser.getInterests().stream().map(InterestEntity::getName).collect(Collectors.toSet())
        );
    }

    @Cacheable(value = "users", key = "#id")
    public UserResponse getUserById(int id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getGender(),
                user.getAge(),
                user.getInterests().stream().map(InterestEntity::getName).collect(Collectors.toSet())
        );
    }

    @Cacheable(value = "users", key = "#name")
    public UserResponse getUserByName(String name) {
        UserEntity user = userRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException("User not found with name: " + name));

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getGender(),
                user.getAge(),
                user.getInterests().stream().map(InterestEntity::getName).collect(Collectors.toSet())
        );
    }

    @CacheEvict(value = "users", key = "#id")  // Remove user from cache
    public void deleteUser(int id) {
    	UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        user.getInterests().clear();
    	userRepository.deleteById(id);
    }

    @CacheEvict(value = "users", key = "#id")  // Remove user from cache
    public UserResponse updateUser(int id, UserRequest userRequest) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return getUserResponse(userRequest, user);
    }

    private UserResponse getUserResponse(UserRequest userRequest, UserEntity user) {
        Optional.ofNullable(userRequest.getName()).ifPresent(user::setName);
        Optional.ofNullable(userRequest.getGender()).ifPresent(user::setGender);
        Optional.of(userRequest.getAge()).filter(age -> age != 0).ifPresent(user::setAge);
//        Optional.ofNullable(userRequest.getInterests())
//                .filter(interests -> !interests.isEmpty())
//                .ifPresent(interestNames -> {
//                    Set<InterestEntity> interestEntities = interestNames.stream()
//                            .map(interestName -> interestRepository.findByName(interestName)
//                                    .orElseGet(() -> {
//                                        InterestEntity newInterest = new InterestEntity();
//                                        newInterest.setName(interestName);
//                                        return interestRepository.save(newInterest);
//                                    }))
//                            .collect(Collectors.toSet());
//                    user.setInterests(interestEntities);
//                });
        Set<String> interestNames = Optional.ofNullable(user.getInterests())
                .orElseGet(HashSet::new)  // Ensures we don't get null
                .stream()
                .map(InterestEntity::getName)
                .collect(Collectors.toSet());

        UserEntity updatedUser = userRepository.save(user);
        return new UserResponse(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getGender(),
                updatedUser.getAge(),
                updatedUser.getInterests().stream().map(InterestEntity::getName).collect(Collectors.toSet())
        );
    }

    @CacheEvict(value = "users", key = "#name") // Remove user from cache
    public UserResponse updateUserByName(String name, UserRequest userRequest) {
        UserEntity user = userRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException("User not found with name: " + name));

        // Use Optional to update only present fields
        return getUserResponse(userRequest, user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
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