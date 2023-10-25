package com.example.concurrency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ConcurrencyApplication implements CommandLineRunner {

    private final BlockingObjectPool objectPool;
    @Autowired
    public ConcurrencyApplication(BlockingObjectPool objectPool) {
        this.objectPool = objectPool;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConcurrencyApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Starting Object Pool application...");

        // 1. Retrieving objects from the pool until it's empty.
        for (int i = 0; i < objectPool.getSize(); i++) {
            Object obj = objectPool.get();
            log.info("Retrieved object from pool: {}", obj.hashCode());
        }

        // 2. Demonstrating blocking behavior when trying to retrieve from an empty pool.
        Thread t1 = new Thread(() -> {
            log.info("Trying to retrieve from an empty pool (should block)...");
            Object obj = objectPool.get();
            log.info("Unblocked and retrieved object: {}", obj.hashCode());
        });
        t1.start();

        // Give t1 some time to start and block
        try { Thread.sleep(2000); } catch (InterruptedException e) { /* ignore */ }

        // 3. Returning objects to the pool and showing how t1 gets unblocked.
        Object newObj = new Object();
        objectPool.take(newObj);
        log.info("Added object to pool: {}", newObj.hashCode());

        // Give t1 some time to unblock and finish
        try { t1.join(); } catch (InterruptedException e) { /* ignore */ }

        // Fill the pool to its limit
        for (int i = 1; i < objectPool.getSize(); i++) {
            objectPool.take(new Object());
        }
        log.info("Filled the pool with objects.");

        // 4. Demonstrating blocking behavior when trying to add to a full pool.
        Thread t2 = new Thread(() -> {
            log.info("Trying to add to a full pool (should block)...");
            objectPool.take(new Object());
            log.info("Unblocked and added object to the pool.");
        });
        t2.start();

        // Give t2 some time to start and block
        try { Thread.sleep(2000); } catch (InterruptedException e) { /* ignore */ }

        // 5. Retrieving an object to unblock t2.
        Object obj2 = objectPool.get();
        log.info("Retrieved object from pool: {}", obj2.hashCode());

        // Give t2 some time to unblock and finish
        try { t2.join(); } catch (InterruptedException e) { /* ignore */ }
    }
}