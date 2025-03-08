package com.estuate.datingapp.datingrecommendationengine.repository;

import com.estuate.datingapp.datingrecommendationengine.entity.InterestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterestRepository extends JpaRepository<InterestEntity, Integer> {

    Optional<InterestEntity> findByName(String name);
}
