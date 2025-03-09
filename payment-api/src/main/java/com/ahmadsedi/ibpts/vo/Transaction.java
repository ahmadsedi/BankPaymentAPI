package com.ahmadsedi.ibpts.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@code Transaction} represents a transaction for debit/credit an account, once it has done successfully.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 11:33
 */

@Getter
@Setter
public class Transaction {
    private int id;
    @JsonProperty("account-id")
    private int accountId;
    private double amount;
    @JsonProperty("transaction-date")
    private String transactionDate;
    private String type;
}
