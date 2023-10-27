package com.example.concurrency.services;

import com.example.concurrency.data.entities.Currency;
import com.example.concurrency.data.entities.ExchangeRate;
import com.example.concurrency.data.repository.ExchangeRateDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateDao exchangeRateDao;

    public ExchangeRate getRate(Currency fromCurrency, Currency toCurrency) {
        return exchangeRateDao.getRate(fromCurrency, toCurrency);
    }

    public void setRate(Currency fromCurrency, Currency toCurrency, BigDecimal rate) {
        ExchangeRate exchangeRate = new ExchangeRate(fromCurrency, toCurrency, rate);
        exchangeRateDao.saveRate(exchangeRate);
    }

    public boolean rateExists(Currency fromCurrency, Currency toCurrency) {
        ExchangeRate rate = getRate(fromCurrency, toCurrency);
        return rate != null;
    }
}