package com.example.concurrency;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

@SpringBootApplication
public class ConcurrencyApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ConcurrencyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        SharedCollection sharedCollection = new SharedCollection();

        // Thread 1: Write random numbers to the collection
        Thread writerThread = new Thread(() -> {
            Random random = new Random();
            while (true) {
                int randomNumber = random.nextInt(100); // Numbers between 0 and 99
                sharedCollection.addNumber(randomNumber);
                try {
                    Thread.sleep(10); // Slow it down a bit
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Thread 2: Print sum of the numbers
        Thread sumThread = new Thread(() -> {
            while (true) {
                System.out.println("Sum: " + sharedCollection.sumOfNumbers());
                try {
                    Thread.sleep(10); // Slow it down a bit
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Thread 3: Print square root of sum of squares of all numbers
        Thread sqrtThread = new Thread(() -> {
            while (true) {
                System.out.println("Square Root of Sum of Squares: " + sharedCollection.squareRootOfSumOfSquares());
                try {
                    Thread.sleep(10); // Slow it down a bit
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        writerThread.start();
        sumThread.start();
        sqrtThread.start();
    }

}
