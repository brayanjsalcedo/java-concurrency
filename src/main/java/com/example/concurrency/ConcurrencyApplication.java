package com.example.concurrency;

import com.example.concurrency.data.entities.Currency;
import com.example.concurrency.data.entities.UserAccount;
import com.example.concurrency.services.AccountService;
import com.example.concurrency.services.CurrencyService;
import com.example.concurrency.utils.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class ConcurrencyApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ConcurrencyApplication.class, args);
    }

    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    @Override
    public void run(String... args) throws Exception {
        Currency usd = currencyService.createCurrency("USD", "US Dollar");
        Currency eur = currencyService.createCurrency("EUR", "Euro");

        UserAccount account = accountService.createAccount("user123");
        accountService.deposit(account, usd, new BigDecimal("1000"));
        accountService.deposit(account, eur, new BigDecimal("500"));

        log.info("Initial Account Balances:");
        accountService.printBalances(account);

        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                try {
                    accountService.exchangeCurrency(account, usd, eur, new BigDecimal("50"));
                } catch (Exception e) {
                    log.error("Error in currency exchange", e);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        log.info("Final Account Balances:");
        accountService.printBalances(account);
    }

}
