package com.demo.adnetwork.util;

import com.demo.adnetwork.entity.Currency;
import org.junit.jupiter.api.Test;

import static com.demo.adnetwork.util.RevenueValueParser.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RevenueValueParserTest
{
    @Test
    void parseRevenueValue()
    {
        RevenueValueParser.RevenueValue value = parse("123", "Revenue (USD)");
        assertEquals(Currency.USD, value.getCurrency());
        assertEquals("123", value.getRevenue());

        value = parse("123", "Revenue (EUR)");
        assertEquals(Currency.EUR, value.getCurrency());
        assertEquals("123", value.getRevenue());

        value = parse("123€", "Revenue (EUR)");
        assertEquals(Currency.EUR, value.getCurrency());
        assertEquals("123", value.getRevenue());

        value = parse("€123", "Revenue");
        assertEquals(Currency.EUR, value.getCurrency());
        assertEquals("123", value.getRevenue());
    }

    @Test
    void parseIllegalCombo()
    {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> parse("€123", "Revenue (USD)"));
        assertEquals("Invalid combination valueOfSymbol revenue header and value!", exception.getMessage());
    }

    @Test
    void parseIllegalHeader()
    {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> parse("€123", "Reven"));
        assertEquals("Currency not found for header (Reven)", exception.getMessage());
    }
}
