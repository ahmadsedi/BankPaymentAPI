package com.ahmadsedi.ibpts.exceptions;

/**
 * The {@code InsufficientFundsException} represents a situation, in which there is a transfer request from a bank
 * account with insufficient funds.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 11:44
 */

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
