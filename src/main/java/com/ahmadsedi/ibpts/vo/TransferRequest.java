package com.ahmadsedi.ibpts.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * The {@code TransferRequest} represents a transfer request.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 13:43
 */

@Getter
@Setter
public class TransferRequest {
    int sourceAccountId;
    int destAccountId;
    double amount;
}
