package com.example.graphql.springnetflixdgs.hello.service;

import com.example.graphql.generated.types.Address;
import com.example.graphql.generated.types.Author;
import com.example.graphql.generated.types.Book;
import com.example.graphql.generated.types.ReleaseHistory;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FakerBookService {

    private Faker faker;

    public FakerBookService(Faker faker) {
        this.faker = faker;
    }

    public static final List<Book> BOOK_LIST = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        for (int i = 0; i < 20; i++) {
            var addresses = new ArrayList<Address>();
            var author = Author.newBuilder().addresses(addresses)
                    .name(faker.book().author())
                    .originCountry(faker.country().name())
                    .build();
            var released = ReleaseHistory.newBuilder()
                    .printedEdition(faker.bool().bool())
                    .releasedCountry(faker.country().name())
                    .year(faker.number().numberBetween(2019, 2022))
                    .build();
            var book = Book.newBuilder().author(author)
                    .publisher(faker.book().publisher())
                    .title(faker.book().title())
                    .released(released)
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

            BOOK_LIST.add(book);
        }
    }

}
