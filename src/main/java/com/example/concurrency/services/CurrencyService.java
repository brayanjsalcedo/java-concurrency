package com.example.concurrency.services;

import com.example.concurrency.data.entities.Currency;
import com.example.concurrency.data.repository.CurrencyDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyDao currencyDao;

    public Currency getCurrencyByCode(String code) {
        return currencyDao.getCurrency(code);
    }

    public void addOrUpdateCurrency(Currency currency) {
        currencyDao.saveCurrency(currency);
    }

    public boolean currencyExists(String code) {
        Currency currency = getCurrencyByCode(code);
        return currency != null;
    }

    public Currency createCurrency(String code, String name) {
        if (currencyExists(code)) {
            log.error("Currency with code {} already exists!", code);
            throw new RuntimeException("Currency with code " + code + " already exists!");
        }

        Currency currency = new Currency(code, name);
        currencyDao.saveCurrency(currency);
        log.info("Currency with code {} created successfully.", code);
        return currency;
    }
}