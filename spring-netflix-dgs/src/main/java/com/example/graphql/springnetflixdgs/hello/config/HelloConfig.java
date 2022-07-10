package com.example.graphql.springnetflixdgs.hello.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfig {

    @Bean
    public Faker faker(){
        return new Faker();
    }
}
