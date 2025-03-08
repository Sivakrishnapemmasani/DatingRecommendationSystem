package com.estuate.datingapp.datingrecommendationengine.repository;

import com.estuate.datingapp.datingrecommendationengine.entity.InterestEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InterestRepositoryTest {

    @Autowired
    private InterestRepository interestRepository;

    @Test
    public void testFindByName() {
        // Given
        InterestEntity interest = new InterestEntity("Reading");
        interestRepository.save(interest);

        // When
        Optional<InterestEntity> foundInterest = interestRepository.findByName("Reading");

        // Then
        assertThat(foundInterest).isPresent();
        assertThat(foundInterest.get().getName()).isEqualTo("Reading");
    }

    @Test
    public void testSaveInterest() {
        // Given
        InterestEntity interest = new InterestEntity("Traveling");

        // When
        InterestEntity savedInterest = interestRepository.save(interest);

        // Then
        assertThat(savedInterest).isNotNull();
        assertThat(savedInterest.getId()).isGreaterThan(0);
        assertThat(savedInterest.getName()).isEqualTo("Traveling");
    }

    @Test
    public void testDeleteInterest() {
        // Given
        InterestEntity interest = new InterestEntity("Cooking");
        InterestEntity savedInterest = interestRepository.save(interest);

        // When
        interestRepository.deleteById(savedInterest.getId());
        Optional<InterestEntity> deletedInterest = interestRepository.findById(savedInterest.getId());

        // Then
        assertThat(deletedInterest).isNotPresent();
    }
}
