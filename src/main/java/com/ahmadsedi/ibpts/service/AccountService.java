package com.ahmadsedi.ibpts.service;

import com.ahmadsedi.ibpts.data.entity.AccountEntity;
import com.ahmadsedi.ibpts.data.entity.TransactionEntity;
import com.ahmadsedi.ibpts.vo.TransferRequest;
import com.ahmadsedi.ibpts.vo.TransferResponse;

import java.util.List;
import java.util.Optional;

/**
 * The {@code AccountService} interface defines account related methods to validate, persist, query accounts, and
 * transferring money between accounts.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 22:47
 */

public interface AccountService {
    /**
     * Creates a bank account by storing the account details.
     *
     * @param accountEntity represents the durable account details.
     * @return the stored bank account.
     */
    AccountEntity createAccount(AccountEntity accountEntity);

    /**
     * Finds the bank account corresponding to accountId.
     *
     * @param accountId is the unique account id
     * @return an Optional which contains an AccountEntity corresponding to the valid existence account id.
     */
    Optional<AccountEntity> findByAccountId(int accountId);

    /**
     * Transfers an amount of money from one account to another account.
     *
     * @param transferRequest represent the amount, source account, and destination account.
     * @return the result of transfer transaction.
     */
    TransferResponse transfer(TransferRequest transferRequest);

    /**
     * Provides a short report (at  most 20 most recent) of an account.
     * @param accountEntity represent the account, the query is executed against that.
     * @return list of transactions.
     */
    List<TransactionEntity> getMiniReport(AccountEntity accountEntity);
}
