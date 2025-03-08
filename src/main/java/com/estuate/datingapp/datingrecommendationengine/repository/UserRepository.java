package com.estuate.datingapp.datingrecommendationengine.repository;

import com.estuate.datingapp.datingrecommendationengine.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByName(String name);
}
