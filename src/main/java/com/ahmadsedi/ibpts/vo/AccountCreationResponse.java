package com.ahmadsedi.ibpts.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * The {@code AccountCreationResponse} represents a response for account creation, once it has created successfully.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 15:50
 */

@Getter
@Setter
public class AccountCreationResponse {
    private int accountId;
    private double balance;
    private String created;
}
