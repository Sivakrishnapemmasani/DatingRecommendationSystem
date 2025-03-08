package com.estuate.datingapp.datingrecommendationengine.dto;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRequestTest {

    @Test
    public void testUserRequestDefaultConstructor() {
        UserRequest userRequest = new UserRequest();
        assertThat(userRequest.getName()).isNull();
        assertThat(userRequest.getGender()).isNull();
        assertThat(userRequest.getAge()).isEqualTo(0);
        assertThat(userRequest.getInterests()).isNull();
    }

    @Test
    public void testUserRequestParameterizedConstructor() {
        Set<String> interests = Set.of("Reading", "Traveling");
        UserRequest userRequest = new UserRequest("John Doe", "Male", 30, interests);

        assertThat(userRequest.getName()).isEqualTo("John Doe");
        assertThat(userRequest.getGender()).isEqualTo("Male");
        assertThat(userRequest.getAge()).isEqualTo(30);
        assertThat(userRequest.getInterests()).isEqualTo(interests);
    }

    @Test
    public void testSettersAndGetters() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Jane Doe");
        userRequest.setGender("Female");
        userRequest.setAge(25);
        Set<String> interests = Set.of("Cooking", "Hiking");
        userRequest.setInterests(interests);

        assertThat(userRequest.getName()).isEqualTo("Jane Doe");
        assertThat(userRequest.getGender()).isEqualTo("Female");
        assertThat(userRequest.getAge()).isEqualTo(25);
        assertThat(userRequest.getInterests()).isEqualTo(interests);
    }
}