package com.ahmadsedi.ibpts.controller;

import com.ahmadsedi.ibpts.vo.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The {@code AccountEndpoint} interface defines the exposed restful services in the account bounded context.
 * It includes endpoints to create an account, query an account for balance, transfer money between internal bank
 * accounts, and a mini-report for the 20 most recent transactions of an account.
 *
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 11:16
 */
public interface AccountEndpoint {

    /**
     * Sample usage, see below.
     *
     * curl -X POST $HOST:$PORT/accounts \
     *   -H "Content-Type: application/json" --data \
     *   '{"balance":50}'
     *
     * @param accountCreationRequest A JSON representation of the new account
     * @return A JSON representation of the newly created account
     */
    @PostMapping(
            value    = "/accounts",
            consumes = "application/json",
            produces = "application/json")
    Mono<AccountCreationResponse> createAccount(@RequestBody AccountCreationRequest accountCreationRequest);

    /**
     * Sample usage, see below.
     *
     * curl -X POST $HOST:$PORT/transaction \
     *   -H "Content-Type: application/json" --data \
     *   '{"accountId":1, "amount": 30, }'
     *
     * @param transferRequest A JSON representation of a transfer
     * @return A JSON representation of the two transactions (source, dest)
     */
    @PostMapping(
            value    = "/accounts/transfer",
            consumes = "application/json",
            produces = "application/json")
    Mono<TransferResponse> transfer(@RequestBody TransferRequest transferRequest);


    /**
     * Sample usage: "curl $HOST:$PORT/accounts/1/balance".
     *
     * @param accountId Id of the account
     * @return the account, if found, else "404, not found"
     */
    @GetMapping(
            value = "/accounts/{accountId}/balance",
            produces = "application/json")
    Mono<BalanceQueryResponse> getBalance(@PathVariable int accountId);

    /**
     * Sample usage: "curl $HOST:$PORT/accounts/1/statements/mini".
     *
     * @param accountId Id of the account
     * @return list of at most 20 recent transactions for the account"
     */
    @GetMapping(
            value = "/accounts/{accountId}/statements/mini",
            produces = "application/json")
    Flux<Transaction> miniReport(@PathVariable int accountId);
}
