package com.example.graphql.springnetflixdgs.hello.service;

import com.example.graphql.generated.types.Hello;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FakerHelloService {

    private final Faker faker;

    public FakerHelloService(Faker faker) {
        this.faker = faker;
    }

    public static final List<Hello> HELLO_LIST = new ArrayList<>();

    @PostConstruct
    private void postConstruct(){
        for (int i = 0; i < 20; i++) {
            HELLO_LIST.add(Hello.newBuilder().randomNumber(faker.random().nextInt(1000))
                    .text(faker.animal().name()).build());
        }
    }
}
