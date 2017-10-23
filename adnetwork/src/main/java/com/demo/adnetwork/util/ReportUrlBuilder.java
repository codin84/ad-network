package com.demo.adnetwork.util;

import com.demo.adnetwork.entity.AdNetworkSource;
import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReportUrlBuilder {
    public static String build(@Nonnull final AdNetworkSource adNetworkSource, @Nonnull final LocalDate date) {
        Preconditions.checkNotNull(adNetworkSource, "AdNetworkSource must not be null!");
        Preconditions.checkNotNull(date, "Date must not be null!");

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(adNetworkSource.getDateParamFormat());
        return adNetworkSource.getUrl().replace("&dateParam", date.format(formatter));
    }
}
