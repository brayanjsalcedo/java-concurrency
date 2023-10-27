package com.example.concurrency.data.repository;

import com.example.concurrency.data.entities.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Repository
public class CurrencyDao {

    private static final String DIRECTORY = "db/currencies";

    public Currency getCurrency(String code) {
        Path path = Paths.get(DIRECTORY, code + ".txt");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            return (Currency) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("Error reading currency from file", e);
            return null;
        }
    }

    public void saveCurrency(Currency currency) {
        Path path = Paths.get(DIRECTORY, currency.getCode() + ".txt");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            oos.writeObject(currency);
        } catch (IOException e) {
            log.error("Error writing currency to file", e);
        }
    }
}