package com.ahmadsedi.ibpts.controller;

import com.ahmadsedi.ibpts.data.entity.AccountEntity;
import com.ahmadsedi.ibpts.vo.Account;
import org.springframework.stereotype.Component;

/**
 * The {@code AccountMapper} handles conversion between HTTP requests to domain objects, and domain objects to
 * HTTP responses for account creation.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 16:13
 */
@Component
public class AccountMapper {

    /**
     * Converts {@code AccountCreationRequest} which represents a HTTP request for account creation to
     * {@code AccountEntity} to be processed in business classes.
     *
     * @param account represents the account to be created.
     * @return the corresponding AccountEntity to the request.
     */
    public AccountEntity apiToEntity(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setBalance(account.getBalance());
        return accountEntity;
    }




    /**
     * Converts {@code AccountEntity} which represents the account creation to
     * {@code AccountCreationResponse} to be serialized in HTTP response to the request.
     *
     * @param accountEntity represents the account which has been created.
     * @return the corresponding AccountCreationResponse to created account object.
     */

    public Account entityToApi(AccountEntity accountEntity) {
        Account accountCreationResponse = new Account();
        accountCreationResponse.setBalance(accountEntity.getBalance());
        accountCreationResponse.setCreated(accountEntity.getCreated().toString());
        accountCreationResponse.setAccountId(accountEntity.getId());
        return accountCreationResponse;
    }
}
