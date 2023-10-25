package com.example.concurrency;

import java.util.Random;
import java.util.UUID;

public class Producer implements Runnable {
    private final MessageBus bus;
    private final Random random = new Random();
    private volatile boolean running = true;

    public Producer(MessageBus bus) {
        this.bus = bus;
    }

    @Override
    public void run() {
        while (running) {
            String topic = "topic-" + random.nextInt(5); // we have 5 topics
            String payload = UUID.randomUUID().toString();
            Message message = new Message(topic, payload);
            bus.postMessage(message);

            try {
                Thread.sleep(random.nextInt(1000)); // random delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false; // if interrupted, stop producing messages
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}