package com.demo.adnetwork.entity;

import com.demo.adnetwork.util.StringUtils;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Objects;

public enum Currency {
    USD("$"),
    GBP("£"),
    EUR("€");

    final String symbol;

    Currency(@Nonnull final String symbol) {
        this.symbol = StringUtils.checkNotBlank(symbol, "Symbol must not be blank!");
    }

    public String getSymbol() {
        return symbol;
    }

    public static Currency valueOfSymbol(@Nonnull final String symbol) {
        StringUtils.checkNotBlank(symbol, "Symbol must not be empty!");
        return Arrays.stream(values())
                .filter(currency -> Objects.equals(symbol, currency.getSymbol()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Currency not defined for symbol (" + symbol + ")"));
    }
}
