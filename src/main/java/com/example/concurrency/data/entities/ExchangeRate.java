package com.example.concurrency.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate {
    private Currency fromCurrency;
    private Currency toCurrency;
    private BigDecimal rate;
}