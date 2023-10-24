package com.example.concurrency.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ConcurrencyService {

    public void runWithConcurrentHashMap() {
        Map<Integer, Integer> map = new ConcurrentHashMap<>();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                map.put(i, i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (Integer key : map.keySet()) {
                log.info("ConcurrentHashMap {}", map.get(key));
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            log.error("Thread interrupted", e);
        }

        log.info("Size of ConcurrentHashMap: {}", map.size());
    }

    public void runWithSynchronizedMap() {
        Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<>());

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                map.put(i, i);
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized(map) {
                for (Integer key : map.keySet()) {
                    log.info("synchronizedMap {}", map.get(key));
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            log.error("Thread interrupted", e);
        }

        log.info("Size of synchronizedMap: {}", map.size());
    }

    public void runWithHashMap() {
        Map<Integer, Integer> map = new HashMap<>();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                map.put(i, i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (Integer key : map.keySet()) {
                log.info("HashMap {}", map.get(key));
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            log.error("Thread interrupted", e);
        }

        log.info("Size of HashMap: {}", map.size());

    }

}