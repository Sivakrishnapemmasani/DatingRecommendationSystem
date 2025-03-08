package com.estuate.datingapp.datingrecommendationengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DatingRecommendationEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatingRecommendationEngineApplication.class, args);
    }

}
