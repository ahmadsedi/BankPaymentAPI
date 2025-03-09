package com.ahmadsedi.ibpts.controller;

import com.ahmadsedi.ibpts.config.FraudProperties;
import com.ahmadsedi.ibpts.exceptions.InvalidAccountDetailsException;
import com.ahmadsedi.ibpts.data.entity.AccountEntity;
import com.ahmadsedi.ibpts.exceptions.InvalidBalanceException;
import com.ahmadsedi.ibpts.service.DatabaseAccountService;
import com.ahmadsedi.ibpts.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.logging.Level.FINE;

/**
 * The {@code AccountEndpointImpl} is the concrete implementation for interface {@code AccountEndpoint}.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 11:14
 */
@RestController
public class AccountEndpointImpl implements AccountEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(AccountEndpointImpl.class);

    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;
    private final DatabaseAccountService databaseAccountService;

    private final FraudProperties fraudProperties;
    private final Scheduler jdbcScheduler;

    public AccountEndpointImpl(AccountMapper accountMapper, TransactionMapper transactionMapper,
                               DatabaseAccountService databaseAccountService,
                               FraudProperties fraudProperties, @Qualifier("jdbcScheduler") Scheduler jdbcScheduler) {
        this.accountMapper = accountMapper;
        this.transactionMapper = transactionMapper;
        this.databaseAccountService = databaseAccountService;
        this.fraudProperties = fraudProperties;
        this.jdbcScheduler = jdbcScheduler;
    }

    @Override
    public Mono<Account> createAccount(Account account) {
        LOG.error("Fraud Detection: "+fraudProperties.isDetection());
        return Mono.fromCallable(() -> internalCreateAccount(account))
                .subscribeOn(jdbcScheduler);
    }

    private Account internalCreateAccount(Account account) {
        AccountEntity entity = accountMapper.apiToEntity(account);
        AccountEntity newEntity = databaseAccountService.createAccount(entity);

        Account newAccount = accountMapper.entityToApi(newEntity);
        LOG.debug("createAccount: created a account entity with balance: {}", account.getBalance());
        return newAccount;
    }

    @Override
    public Mono<TransferResponse> transfer(TransferRequest transferRequest) {
        LOG.debug("transferring from: {} to: {} with amount: {} successfully done.",
                transferRequest.getSourceAccountId(), transferRequest.getSourceAccountId(), transferRequest.getAmount());
        return Mono.fromCallable(() -> databaseAccountService.transfer(transferRequest))
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<BalanceQueryResponse> getBalance(int accountId) {
        return Mono.fromCallable(() -> internalGetBalance(accountId))
                .subscribeOn(jdbcScheduler);
    }

    private BalanceQueryResponse internalGetBalance(int accountId){
        AccountEntity accountEntity = getAccountEntity(accountId);
        LOG.debug("getAccount called for accountId: {}", accountId);
        BalanceQueryResponse balanceQueryResponse = new BalanceQueryResponse(accountId, accountEntity.getBalance());
        return balanceQueryResponse;
    }

    @Override
    public Flux<Transaction> miniReport(int accountId) {
        return Mono.fromCallable(() -> internalMiniReport(accountId))
                .flatMapMany(Flux::fromIterable)
                .log(LOG.getName(), FINE)
                .subscribeOn(jdbcScheduler);
    }

    private List<Transaction> internalMiniReport(int accountId){
        AccountEntity accountEntity = getAccountEntity(accountId);
        return databaseAccountService.getMiniReport(accountEntity).stream().map(t-> transactionMapper.entityToApi(t)).collect(Collectors.toList());
    }

    private AccountEntity getAccountEntity(int accountId){
        if (accountId < 1) {
            throw new InvalidAccountDetailsException("Invalid account details, accountId: " + accountId);
        }
        return databaseAccountService.findByAccountId(accountId)
                .orElseThrow(() -> new InvalidAccountDetailsException("Invalid account details, accountId: " + accountId));
    }
}