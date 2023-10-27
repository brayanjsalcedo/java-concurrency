package com.example.concurrency.utils;

import com.example.concurrency.data.entities.Currency;
import com.example.concurrency.data.entities.ExchangeRate;
import com.example.concurrency.services.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyConverter {
    private final ExchangeRateService exchangeRateService;

    public BigDecimal convert(Currency fromCurrency, Currency toCurrency, BigDecimal amount) {
        ExchangeRate rate = exchangeRateService.getRate(fromCurrency, toCurrency);
        return amount.multiply(rate.getRate());
    }
}