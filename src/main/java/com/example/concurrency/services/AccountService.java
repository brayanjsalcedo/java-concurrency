package com.example.concurrency.services;

import com.example.concurrency.data.entities.Currency;
import com.example.concurrency.data.entities.UserAccount;
import com.example.concurrency.data.repository.UserAccountDao;
import com.example.concurrency.utils.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserAccountDao userAccountDao;
    private final CurrencyConverter currencyConverter;

    public UserAccount createAccount(String accountId) {
        return new UserAccount(accountId, new HashMap<>());
    }

    public void deposit(UserAccount account, Currency currency, BigDecimal amount) {
        account.getCurrencyBalances().merge(currency, amount, BigDecimal::add);
        userAccountDao.saveAccount(account);
    }

    public void exchangeCurrency(UserAccount account, Currency fromCurrency, Currency toCurrency, BigDecimal amount) {
        synchronized(account) {
            BigDecimal convertedAmount = currencyConverter.convert(fromCurrency, toCurrency, amount);
            account.getCurrencyBalances().computeIfPresent(fromCurrency, (k, v) -> v.subtract(amount));
            account.getCurrencyBalances().merge(toCurrency, convertedAmount, BigDecimal::add);
            userAccountDao.saveAccount(account);
        }
    }

    public void printBalances(UserAccount account) {
        log.info("Balances for account {}: ", account.getAccountId());
        account.getCurrencyBalances().forEach((currency, balance) -> {
            log.info("{}: {}", currency.getCode(), balance);
        });
    }
}