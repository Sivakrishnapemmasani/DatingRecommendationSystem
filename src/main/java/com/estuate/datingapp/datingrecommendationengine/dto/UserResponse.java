package com.estuate.datingapp.datingrecommendationengine.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;


public class UserResponse {

    // Getters and Setters
    private int id;
    private String name;
    private String gender;
    private int age;
    private Set<String> interests;

    public UserResponse(int id, String name, String gender, int age, Set<String> interests) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.interests = interests;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<String> getInterests() {
		return interests;
	}

	public void setInterests(Set<String> interests) {
		this.interests = interests;
	}

    
}