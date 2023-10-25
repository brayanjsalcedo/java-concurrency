package com.example.concurrency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageBus {
    private final Map<String, List<Message>> topics = new HashMap<>();

    public synchronized void postMessage(Message message) {
        topics.putIfAbsent(message.topic(), new ArrayList<>());
        topics.get(message.topic()).add(message);
        notifyAll();
    }

    public synchronized Message consumeMessage(String topic) throws InterruptedException {
        while (!topics.containsKey(topic) || topics.get(topic).isEmpty()) {
            wait();
        }
        return topics.get(topic).remove(0);
    }
}