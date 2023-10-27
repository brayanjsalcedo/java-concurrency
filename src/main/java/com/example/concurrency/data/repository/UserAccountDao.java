package com.example.concurrency.data.repository;

import com.example.concurrency.data.entities.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Repository
public class UserAccountDao {

    private static final String DIRECTORY = "db/user_accounts";

    public UserAccount getAccount(UUID accountId) {
        Path path = Paths.get(DIRECTORY, accountId.toString() + ".txt");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            return (UserAccount) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("Error reading user account from file", e);
            return null;
        }
    }

    public void saveAccount(UserAccount account) {
        Path path = Paths.get(DIRECTORY, account.getAccountId() + ".txt");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            oos.writeObject(account);
        } catch (IOException e) {
            log.error("Error writing user account to file", e);
        }
    }

    // Assuming deletion might be needed
    public boolean deleteAccount(UUID accountId) {
        Path path = Paths.get(DIRECTORY, accountId.toString() + ".txt");
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("Error deleting user account file", e);
            return false;
        }
    }
}