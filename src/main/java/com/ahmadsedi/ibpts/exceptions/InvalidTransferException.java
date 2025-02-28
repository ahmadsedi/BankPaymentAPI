package com.ahmadsedi.ibpts.exceptions;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 01/03/2025
 * Time: 00:03
 */

public class InvalidTransferException extends RuntimeException {
    public InvalidTransferException(String message) {
        super(message);
    }
}
