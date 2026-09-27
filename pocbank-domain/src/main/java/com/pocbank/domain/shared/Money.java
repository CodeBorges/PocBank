package com.pocbank.domain.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;


public record Money(
        BigDecimal price,
        Currency currency
) {

    public Money {

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("It's not possible add value less than ZERO");
        }

        price = price.setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);

    }

    public Money plus(Money money){
        if(!Objects.equals(this.currency, money.currency)){
            throw new IllegalArgumentException("It's not possible add different currencies");
        }
        return new Money(this.price.add(money.price), money.currency);
    }

}
