package com.ahmadsedi.ibpts.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@code BalanceQueryResponse} represents a response for balance query execution, once it has created successfully.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 17:50
 */

@Getter
@Setter
@AllArgsConstructor
public class BalanceQueryResponse {
    int accountId;
    double balance;
}
