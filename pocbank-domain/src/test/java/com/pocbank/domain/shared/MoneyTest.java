package com.pocbank.domain.shared;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {

    private final String CURRENCY_CODE = "BRL";

    @Test
    void shouldRejectNegativeValue() {
        BigDecimal value = new BigDecimal("-1.20");
        assertThrows(IllegalArgumentException.class,
                () -> new Money(value, getCurrency(CURRENCY_CODE)));
    }

    @Test
    void shouldAcceptPositiveValue() {
        BigDecimal value = new BigDecimal("1.20");

        var result = new Money(value, getCurrency(CURRENCY_CODE)).price();

        assertEquals(value, result);
    }

    @Test
    void shouldAddUpTotal() {
        BigDecimal value = new BigDecimal("10.00");
        BigDecimal value2 = new BigDecimal("5.50");


        Money result = new Money(value, getCurrency(CURRENCY_CODE));
        Money result2 = new Money(value2, getCurrency(CURRENCY_CODE));


        assertEquals(new BigDecimal("15.50"), result.plus(result2).price());
        assertEquals("BRL", result.plus(result2).currency().getCurrencyCode());
    }

    @Test
    void shouldRejectAddUpDifferentCurrency() {
        BigDecimal value = new BigDecimal("10.00");
        BigDecimal addValue = new BigDecimal("5.50");

        Money result = new Money(value, getCurrency(CURRENCY_CODE));
        Money result2 = new Money(addValue, getCurrency("USD"));



        assertThrows(IllegalArgumentException.class, () -> result.plus(result2));
    }

    @Test
    void shouldBeEqualWhenSameAmountWithDifferentScale(){
        BigDecimal value = new BigDecimal("10.00");
        BigDecimal newValue = new BigDecimal("10.0");

        Money result = new Money(value, getCurrency(CURRENCY_CODE));
        Money result2 = new Money(newValue, getCurrency(CURRENCY_CODE));

        assertEquals(result, result2);
    }

    @Test
    void shouldRoundToCurrencyScale(){
        BigDecimal value = new BigDecimal("10.005");
        BigDecimal newValue = new BigDecimal("10.015");

        Money result = new Money(value, getCurrency(CURRENCY_CODE));
        Money result2 = new Money(newValue, getCurrency(CURRENCY_CODE));

        assertEquals(new BigDecimal("10.00"), result.price());
        assertEquals(new BigDecimal("10.02"), result2.price());
    }

    private Currency getCurrency(String currencyCode) {
        if (StringUtils.isBlank(currencyCode)) {
            return Currency.getInstance(CURRENCY_CODE);
        }

        return Currency.getInstance(currencyCode);
    }
}
