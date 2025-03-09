package com.ahmadsedi.ibpts.vo;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Positive;

/**
 * The {@code AccountCreationRequest} represents a request for account creation.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 15:50
 */

@Getter
@Setter
public class Account {
    @Positive(
            message = "The balance must be greater than zero."
    )
    private double balance;
    private int accountId;
    private String created;
}
