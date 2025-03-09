package com.ahmadsedi.ibpts.exceptions;

/**
 * The {@code InvalidAccountDetailsException} represents a situation, where there is a query or money transfer to/from a
 * non-existence bank account.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 13:56
 */

public class InvalidAccountDetailsException extends RuntimeException {
    public InvalidAccountDetailsException(String message) {
        super(message);
    }
}