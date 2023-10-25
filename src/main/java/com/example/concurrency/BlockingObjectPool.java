package com.example.concurrency;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BlockingObjectPool {

    private List<Object> pool;
    private final Object lock = new Object();

    @Getter
    private final int size = 10;

    @PostConstruct
    public void init() {
        pool = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            pool.add(new Object());
        }
    }

    public Object get() {
        synchronized (lock) {
            while (pool.isEmpty()) {
                try {
                    log.info("Pool is empty. Waiting...");
                    lock.wait();
                } catch (InterruptedException e) {
                    log.error("Interrupted while waiting for an object from pool", e);
                }
            }
            return pool.remove(0);
        }
    }

    public void take(Object object) {
        synchronized (lock) {
            while (pool.size() >= size) {
                try {
                    log.info("Pool is full. Waiting...");
                    lock.wait();
                } catch (InterruptedException e) {
                    log.error("Interrupted while waiting to add an object to the pool", e);
                }
            }
            pool.add(object);
            lock.notifyAll();
        }
    }
}
