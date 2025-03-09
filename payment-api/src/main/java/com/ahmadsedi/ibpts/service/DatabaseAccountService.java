package com.ahmadsedi.ibpts.service;

import com.ahmadsedi.ibpts.controller.AccountMapper;
import com.ahmadsedi.ibpts.data.entity.AccountEntity;
import com.ahmadsedi.ibpts.data.entity.TransactionEntity;
import com.ahmadsedi.ibpts.data.entity.TransactionType;
import com.ahmadsedi.ibpts.data.repo.AccountRepository;
import com.ahmadsedi.ibpts.data.repo.TransactionRepository;
import com.ahmadsedi.ibpts.exceptions.InvalidAccountDetailsException;
import com.ahmadsedi.ibpts.exceptions.InsufficientFundsException;
import com.ahmadsedi.ibpts.vo.TransferRequest;
import com.ahmadsedi.ibpts.vo.TransferResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The {@code DatabaseAccountService} provides a concrete implementation of {@code AccountService} to persist/query
 * account in the database.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 11:09
 */

@Service
public class DatabaseAccountService implements AccountService{

    AccountRepository accountRepository;

    TransactionRepository transactionRepository;

    AccountMapper accountMapper;

    public DatabaseAccountService(AccountRepository accountRepository, TransactionRepository transactionRepository,
                                  AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.accountMapper = accountMapper;
    }

    public AccountEntity createAccount(AccountEntity accountEntity){
        return accountRepository.save(accountEntity);
    }

    public Optional<AccountEntity> findByAccountId(int accountId) {
        return accountRepository.findById(accountId);
    }

    @Transactional
    public TransferResponse transfer(TransferRequest transferRequest) {

        AccountEntity sourceAccountEntity = accountRepository.findById(transferRequest.getSourceAccountId()).
                orElseThrow(()->new InvalidAccountDetailsException("No account found with accountId: " + transferRequest.getSourceAccountId()));
        AccountEntity destAccountEntity = accountRepository.findById(transferRequest.getDestAccountId()).
                orElseThrow(()->new InvalidAccountDetailsException("No account found with accountId: " + transferRequest.getDestAccountId()));
        if(sourceAccountEntity.getBalance()<transferRequest.getAmount()){
            throw new InsufficientFundsException("Insufficient funds available in account: "+ sourceAccountEntity.getId());
        }else{
            //preparing source transaction
            TransactionEntity sourceTransactionEntity = new TransactionEntity();
            sourceTransactionEntity.setType(TransactionType.DEBIT);
            sourceTransactionEntity.setAmount(transferRequest.getAmount());
            sourceTransactionEntity.setAccountEntity(sourceAccountEntity);

            //preparing dest transaction
            TransactionEntity destTransactionEntity = new TransactionEntity();
            destTransactionEntity.setType(TransactionType.CREDIT);
            destTransactionEntity.setAmount(transferRequest.getAmount());
            destTransactionEntity.setAccountEntity(destAccountEntity);


            transactionRepository.save(sourceTransactionEntity);
            transactionRepository.save(destTransactionEntity);

            sourceAccountEntity.setBalance(sourceAccountEntity.getBalance()-transferRequest.getAmount());
            destAccountEntity.setBalance(destAccountEntity.getBalance()+transferRequest.getAmount());

            accountRepository.save(sourceAccountEntity);
            accountRepository.save(destAccountEntity);

            TransferResponse transferResponse = new TransferResponse();
            transferResponse.setAmount(transferRequest.getAmount());
            transferResponse.setSourceTransactionId(sourceTransactionEntity.getId());
            transferResponse.setDestTransactionId(destTransactionEntity.getId());
            return transferResponse;
        }

    }

    public List<TransactionEntity> getMiniReport(AccountEntity accountEntity) {
        return transactionRepository.findTop20ByAccountEntityOrderByCreatedDesc(accountEntity);
    }
}
