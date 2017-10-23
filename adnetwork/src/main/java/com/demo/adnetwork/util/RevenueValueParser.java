package com.demo.adnetwork.util;

import com.demo.adnetwork.entity.Currency;
import com.demo.adnetwork.importer.csv.CsvField;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class RevenueValueParser {
    public static RevenueValue parse(@Nonnull final String value, @Nonnull final String revenueHeader) {
        Assert.hasText(value, "Value must not be empty!");
        Assert.hasText(revenueHeader, "RevenueHeader must not be empty!");

        final boolean currencyIsPartOfValue = CsvField.REVENUE.name().equalsIgnoreCase(revenueHeader);
        final Currency currencyFromValue = getCurrencyFromValue(value);
        if (currencyIsPartOfValue) {
            if (currencyFromValue == null)
                throw new IllegalArgumentException("No currency found!");

            return new RevenueValue(value.replaceAll(currencyFromValue.getSymbol(), ""), currencyFromValue);
        }

        final Currency currencyFromHeader = Arrays.stream(Currency.values())
                .filter(currency -> revenueHeader.toUpperCase().contains(currency.name()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Currency not found for header (" + revenueHeader + ")"));
        if (currencyFromValue != null && currencyFromHeader != currencyFromValue) {
            throw new IllegalArgumentException("Invalid combination valueOfSymbol revenue header and value!");
        }
        return new RevenueValue(value.replaceAll(currencyFromHeader.getSymbol(), ""), currencyFromHeader);
    }

    private static Currency getCurrencyFromValue(@Nonnull String value) {
        Assert.hasText(value, "Value must not be empty!");
        return Arrays.stream(Currency.values())
                .filter(currency -> value.startsWith(currency.getSymbol()) || value.endsWith(currency.getSymbol()))
                .findFirst()
                .orElse(null);
    }

    public static class RevenueValue {
        private String revenue;
        private Currency currency;

        RevenueValue(@Nonnull final String revenue, @Nonnull final Currency currency) {
            Assert.hasText(revenue, "Revenue must not be empty!");
            Assert.notNull(currency, "Currency must not be null!");

            this.revenue = revenue;
            this.currency = currency;
        }

        public String getRevenue() {
            return revenue;
        }

        public Currency getCurrency() {
            return currency;
        }
    }
}
