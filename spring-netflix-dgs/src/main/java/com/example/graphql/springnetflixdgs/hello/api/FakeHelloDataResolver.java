package com.example.graphql.springnetflixdgs.hello.api;

import com.example.graphql.generated.types.Hello;
import com.example.graphql.springnetflixdgs.hello.service.FakerHelloService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@DgsComponent
public class FakeHelloDataResolver {

    @DgsQuery
    public List<Hello> allHellos(){
        return FakerHelloService.HELLO_LIST;
    }

    @DgsQuery
    public Hello oneHello(){
        return FakerHelloService.HELLO_LIST.get(
                ThreadLocalRandom.current().nextInt(FakerHelloService.HELLO_LIST.size())
        );
    }
}
