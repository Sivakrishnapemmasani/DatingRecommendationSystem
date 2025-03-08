# Dating Recommendation Engine

## Overview
The *Dating Recommendation Engine* is a Spring Boot-based application that allows users to register, update their profiles, and receive personalized recommendations based on gender, age, and shared interests. It uses *Spring Boot, JPA (Hibernate), H2 database, and **ModelMapper* for efficient data processing.

---

## Features
- *User Registration*: Register users with name, gender, age, and interests.
- *User Management*: Fetch, update, and delete users by ID or name.
- *Recommendation Engine*: Provides best matches based on gender, age, and interests.
- *Caching*: Uses Spring Cache to optimize database queries.

---

## Tech Stack
- *Java 17*
- *Spring Boot 3.4.3*
- *Spring Data JPA* (Hibernate)
- *H2 Database* (In-Memory for testing)
- *Spring Cache* (Caching for faster response)
- *Mockito & JUnit 5* (Unit testing)

---

## Setup Instructions

### 1. Clone the Repository
sh

cd dating-recommendation-engine


### 2. Build the Project
sh
mvn clean install


### 3. Run the Application
sh
mvn spring-boot:run


The application will start at *http://localhost:8050*

---

## API Endpoints

### *User Management APIs*

#### Register User
http
POST /users/register

*Request Body:*
json
{
  "name": "Alice",
  "gender": "Female",
  "age": 25,
  "interests": ["Cricket", "Chess"]
}


#### Get User by ID
http
GET /users/{id}


#### Get User by Name
http
GET /users/name/{name}


#### Update User by ID
http
PUT /users/{id}


#### Delete User
http
DELETE /users/{id}


---

### *Recommendation APIs*

#### Get Recommendations
http
GET /recommendations/username/{username}


#### Get Top N Matches
http
GET /recommendations/username/{username}/top/{limit}


---

## Caching Strategy
The application caches users to reduce database queries using *Spring Cache*.
- @Cacheable("users") for fetching users
- @CacheEvict("users") for updating/deleting users

---

## Testing
### Run Unit & Integration Tests
sh
mvn test

---

## Contributors
- *Siva Krishna Pemmasani

---
