package com.example.concurrency;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConcurrencyApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ConcurrencyApplication.class, args);
    }

    @Override
    public void run(String... args) {
        MessageBus bus = new MessageBus();

        for (int i = 0; i < 3; i++) { // 3 producers
            new Thread(new Producer(bus)).start();
        }

        for (int i = 0; i < 5; i++) { // 5 consumers (one for each topic)
            new Thread(new Consumer(bus, "topic-" + i)).start();
        }
    }

}
