package com.example.concurrency.data.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {
    private String accountId;
    private Map<Currency, BigDecimal> currencyBalances = new HashMap<>();
}