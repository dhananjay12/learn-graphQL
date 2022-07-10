package com.example.graphql.springnetflixdgs.hello.api;

import com.example.graphql.generated.DgsConstants;
import com.example.graphql.generated.types.Book;
import com.example.graphql.generated.types.ReleaseHistory;
import com.example.graphql.generated.types.ReleaseHistoryInput;
import com.example.graphql.springnetflixdgs.hello.service.FakerBookService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

@DgsComponent
public class FakeBookDataResolver {

    @DgsQuery(field = "books")
            public List<Book> booksWrittenBy(@InputArgument(name = "author") Optional<String> authorName) {
        if (authorName.isEmpty() || StringUtils.isBlank(authorName.get())) {
            return FakerBookService.BOOK_LIST;
        }

        return FakerBookService.BOOK_LIST.stream()
                .filter(b -> StringUtils.containsIgnoreCase(
                        b.getAuthor().getName(), authorName.get()
                )).collect(Collectors.toList());
    }

    @DgsQuery(field = "booksByReleased")
            public List<Book> getBooksByReleased(DataFetchingEnvironment dataFetchingEnvironment) {
        var releasedMap = (Map<String, Object>) dataFetchingEnvironment.getArgument("releasedInput");
        var releasedInput = ReleaseHistoryInput.newBuilder()
                .printedEdition((boolean) releasedMap.get(DgsConstants.RELEASEHISTORYINPUT.PrintedEdition))
                .year((int) releasedMap.get(DgsConstants.RELEASEHISTORYINPUT.Year))
                .build();

        return FakerBookService.BOOK_LIST.stream().filter(
                b -> this.matchReleaseHistory(releasedInput, b.getReleased())
        ).collect(Collectors.toList());
    }

    private boolean matchReleaseHistory(ReleaseHistoryInput input, ReleaseHistory element) {
        return input.getPrintedEdition().equals(element.getPrintedEdition())
                && input.getYear() == element.getYear();
    }

}
