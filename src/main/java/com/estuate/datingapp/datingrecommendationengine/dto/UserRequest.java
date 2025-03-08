package com.estuate.datingapp.datingrecommendationengine.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;


public class UserRequest {

    private String name;
    private String gender;
    private int age;
    private Set<String> interests; // Change from Set<InterestRequest> to Set<String>

    // Constructors
    public UserRequest() {}

    public UserRequest(String name, String gender, int age, Set<String> interests) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.interests = interests;
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

	@Override
	public String toString() {
		return "UserRequest [name=" + name + ", gender=" + gender + ", age=" + age + ", interests=" + interests + "]";
	}

    
}
