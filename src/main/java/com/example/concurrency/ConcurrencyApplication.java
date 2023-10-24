package com.example.concurrency;

import com.example.concurrency.services.ConcurrencyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class ConcurrencyApplication implements CommandLineRunner {

    private final ConcurrencyService concurrencyService;

    public static void main(String[] args) {
        SpringApplication.run(ConcurrencyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // concurrencyService.runWithConcurrentHashMap();
        // concurrencyService.runWithSynchronizedMap();
        concurrencyService.runConcurrentModification();
    }

}
