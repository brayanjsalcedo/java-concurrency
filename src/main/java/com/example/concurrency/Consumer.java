package com.example.concurrency;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Consumer implements Runnable {
    private final MessageBus bus;
    private final String topic;
    private volatile boolean running = true;

    public Consumer(MessageBus bus, String topic) {
        this.bus = bus;
        this.topic = topic;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Message message = bus.consumeMessage(topic);
                log.info("Consumed on " + topic + ": " + message.payload());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false; // if interrupted, stop consuming messages
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}