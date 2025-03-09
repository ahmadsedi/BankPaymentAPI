package com.ahmadsedi.ibpts.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * The {@code TransferResponse} represents a transfer response, once it has been successfully done.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 13:45
 */

@Getter
@Setter
public class TransferResponse {
    int sourceTransactionId;
    int destTransactionId;
    double amount;
}
