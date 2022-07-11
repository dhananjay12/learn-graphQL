package com.example.graphql.springnetflixdgs.hello.api;

import com.example.graphql.generated.DgsConstants;
import com.example.graphql.generated.types.MobileApp;
import com.example.graphql.generated.types.MobileAppFilter;
import com.example.graphql.springnetflixdgs.hello.service.FakeMobileService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

@DgsComponent
public class FakeMobileAppDataResolver {

    @DgsQuery(field = "mobileApps")
            public List<MobileApp> getMobileApps(@InputArgument(name = "mobileAppFilter") Optional<MobileAppFilter> filter) {
        if (filter.isEmpty()) {
            return FakeMobileService.MOBILE_APP_LIST;
        }

        return FakeMobileService.MOBILE_APP_LIST.stream().filter(
                mobileApp -> this.matchFilter(filter.get(), mobileApp)
        ).collect(Collectors.toList());
    }

    private boolean matchFilter(MobileAppFilter mobileAppFilter, MobileApp mobileApp) {
        var isAppMatch = StringUtils.containsIgnoreCase(mobileApp.getName(),
                StringUtils.defaultIfBlank(mobileAppFilter.getName(), StringUtils.EMPTY))
                && StringUtils.containsIgnoreCase(mobileApp.getVersion(),
                StringUtils.defaultIfBlank(mobileAppFilter.getVersion(), StringUtils.EMPTY))
                && mobileApp.getReleaseDate().isAfter(
                Optional.ofNullable(mobileAppFilter.getReleasedAfter()).orElse(LocalDate.MIN))
                && mobileApp.getDownloaded() >=
                Optional.ofNullable(mobileAppFilter.getMinimumDownload()).orElse(0);

        if (!isAppMatch) {
            return false;
        }

        if (StringUtils.isNotBlank(mobileAppFilter.getPlatform())
                && !mobileApp.getPlatform().contains(mobileAppFilter.getPlatform().toLowerCase())) {
            return false;
        }

        if (mobileAppFilter.getAuthor() != null
                && !StringUtils.containsIgnoreCase(mobileApp.getAuthor().getName(),
                StringUtils.defaultIfBlank(mobileAppFilter.getAuthor().getName(), StringUtils.EMPTY))) {
            return false;
        }

        if (mobileAppFilter.getCategory() != null
                && !mobileApp.getCategory().equals(mobileAppFilter.getCategory())) {
            return false;
        }

        return true;
    }

}
