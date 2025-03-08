package com.estuate.datingapp.datingrecommendationengine.repository;

import com.estuate.datingapp.datingrecommendationengine.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByName() {
        // Given
        UserEntity user = new UserEntity("John Doe", "Male", 30);
        userRepository.save(user);

        // When
        Optional<UserEntity> foundUser = userRepository.findByName("John Doe");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("John Doe");
    }

    @Test
    public void testSaveUser() {
        // Given
        UserEntity user = new UserEntity("Jane Doe", "Female", 25);

        // When
        UserEntity savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
        assertThat(savedUser.getName()).isEqualTo("Jane Doe");
    }

    @Test
    public void testDeleteUser() {
        // Given
        UserEntity user = new UserEntity("Alice", "Female", 28);
        UserEntity savedUser = userRepository.save(user);

        // When
        userRepository.deleteById(savedUser.getId());
        Optional<UserEntity> deletedUser = userRepository.findById(savedUser.getId());

        // Then
        assertThat(deletedUser).isNotPresent();
    }
}
