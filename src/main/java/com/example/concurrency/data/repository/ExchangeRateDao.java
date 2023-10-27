package com.example.concurrency.data.repository;

import com.example.concurrency.data.entities.Currency;
import com.example.concurrency.data.entities.ExchangeRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Repository
public class ExchangeRateDao {

    private static final String DIRECTORY = "db/exchange_rates";

    public ExchangeRate getRate(Currency fromCurrency, Currency toCurrency) {
        String fileName = fromCurrency.getCode() + "_to_" + toCurrency.getCode() + ".txt";
        Path path = Paths.get(DIRECTORY, fileName);

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            return (ExchangeRate) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("Error reading exchange rate from file", e);
            return null;
        }
    }

    public void saveRate(ExchangeRate rate) {
        String fileName = rate.getFromCurrency().getCode() + "_to_" + rate.getToCurrency().getCode() + ".txt";
        Path path = Paths.get(DIRECTORY, fileName);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            oos.writeObject(rate);
        } catch (IOException e) {
            log.error("Error writing exchange rate to file", e);
        }
    }
}