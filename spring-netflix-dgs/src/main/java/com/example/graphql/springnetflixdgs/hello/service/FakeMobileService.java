package com.example.graphql.springnetflixdgs.hello.service;

import com.example.graphql.generated.types.Address;
import com.example.graphql.generated.types.Author;
import com.example.graphql.generated.types.MobileApp;
import com.example.graphql.generated.types.MobileAppCategory;
import com.github.javafaker.Faker;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FakeMobileService {

    public static final List<MobileApp> MOBILE_APP_LIST = new ArrayList<>();

    private Faker faker;

    public FakeMobileService(Faker faker) {
        this.faker = faker;
    }

    @PostConstruct
    private void postConstruct() throws MalformedURLException {
        for (int i = 0; i < 20; i++) {
            var addresses = new ArrayList<Address>();
            var author = Author.newBuilder().addresses(addresses)
                    .name(faker.app().author())
                    .originCountry(faker.country().name())
                    .build();
            var mobileApp = MobileApp.newBuilder()
                    .name(faker.app().name())
                    .author(author).version(faker.app().version())
                    .platform(randomMobileAppPlatform())
                    .appId(UUID.randomUUID().toString())
                    .releaseDate(LocalDate.now().minusDays(faker.random().nextInt(365)))
                    .downloaded(faker.number().numberBetween(1, 1_500_000))
                    .homepage(new URL("https://" + faker.internet().url()))
                    .category(MobileAppCategory.values()[faker.random().nextInt(MobileAppCategory.values().length)])
                    .build();

            for (int j = 0; j < ThreadLocalRandom.current().nextInt(1, 3); j++) {
                var address = Address.newBuilder()
                        .country(faker.address().country())
                        .city(faker.address().cityName())
                        .country(faker.address().country())
                        .street(faker.address().streetAddress())
                        .zipCode(faker.address().zipCode())
                        .build();

                addresses.add(address);
            }

            MOBILE_APP_LIST.add(mobileApp);
        }
    }

    private List<String> randomMobileAppPlatform() {
        switch (ThreadLocalRandom.current().nextInt(10) % 3) {
            case 0:
                return List.of("android");
            case 1:
                return List.of("ios");
            default:
                return List.of("ios", "android");
        }
    }
}
