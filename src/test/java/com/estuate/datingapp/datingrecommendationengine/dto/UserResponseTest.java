package com.estuate.datingapp.datingrecommendationengine.dto;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserResponseTest {

    @Test
    public void testUserResponseConstructor() {
        Set<String> interests = Set.of("Reading", "Traveling");
        UserResponse userResponse = new UserResponse(1, "John Doe", "Male", 30, interests);

        assertThat(userResponse.getId()).isEqualTo(1);
        assertThat(userResponse.getName()).isEqualTo("John Doe");
        assertThat(userResponse.getGender()).isEqualTo("Male");
        assertThat(userResponse.getAge()).isEqualTo(30);
        assertThat(userResponse.getInterests()).isEqualTo(interests);
    }

    @Test
    public void testSettersAndGetters() {
        UserResponse userResponse = new UserResponse(0, null, null, 0, null);
        userResponse.setId(2);
        userResponse.setName("Jane Doe");
        userResponse.setGender("Female");
        userResponse.setAge(25);
        Set<String> interests = Set.of("Cooking", "Hiking");
        userResponse.setInterests(interests);

        assertThat(userResponse.getId()).isEqualTo(2);
        assertThat(userResponse.getName()).isEqualTo("Jane Doe");
        assertThat(userResponse.getGender()).isEqualTo("Female");
        assertThat(userResponse.getAge()).isEqualTo(25);
        assertThat(userResponse.getInterests()).isEqualTo(interests);
    }
}
