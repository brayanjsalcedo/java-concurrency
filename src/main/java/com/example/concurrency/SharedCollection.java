package com.example.concurrency;

import java.util.ArrayList;
import java.util.List;

public class SharedCollection {
    private final List<Integer> numbers = new ArrayList<>();

    public void addNumber(int number) {
        synchronized (numbers) {
            numbers.add(number);
        }
    }

    public int sumOfNumbers() {
        synchronized (numbers) {
            return numbers.stream().mapToInt(Integer::intValue).sum();
        }
    }

    public double squareRootOfSumOfSquares() {
        synchronized (numbers) {
            double sumOfSquares = numbers.stream().mapToDouble(n -> Math.pow(n, 2)).sum();
            return Math.sqrt(sumOfSquares);
        }
    }
}