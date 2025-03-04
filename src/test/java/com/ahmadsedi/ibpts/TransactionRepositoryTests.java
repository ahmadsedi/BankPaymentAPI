package com.ahmadsedi.ibpts;

import com.ahmadsedi.ibpts.data.entity.AccountEntity;
import com.ahmadsedi.ibpts.data.entity.TransactionEntity;
import com.ahmadsedi.ibpts.data.entity.TransactionType;
import com.ahmadsedi.ibpts.data.repo.AccountRepository;
import com.ahmadsedi.ibpts.data.repo.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 16:56
 */

@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionRepositoryTests {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    TransactionEntity savedEntity;

    AccountEntity accountEntity;


    @BeforeEach
    void setupDb() {
        accountEntity = new AccountEntity();
        accountEntity.setBalance(10);
        accountEntity = accountRepository.save(accountEntity);
        TransactionEntity entity = new TransactionEntity();
        entity.setType(TransactionType.DEBIT);
        entity.setAmount(10);
        entity.setAccountEntity(accountEntity);
        savedEntity = transactionRepository.save(entity);
        assertEqualsTransaction(entity, savedEntity);
    }

    @AfterEach
    void clearData(){
        transactionRepository.deleteAll();
    }


    @Test
    void create() {
        TransactionEntity newTransactionEntity = new TransactionEntity();
        newTransactionEntity.setType(TransactionType.DEBIT);
        newTransactionEntity.setAmount(10);
        newTransactionEntity.setAccountEntity(accountEntity);
        newTransactionEntity = transactionRepository.save(newTransactionEntity);

        TransactionEntity foundEntity = transactionRepository.findById(newTransactionEntity.getId()).get();
        assertEqualsTransaction(newTransactionEntity, foundEntity);

        assertEquals(2, transactionRepository.count());
    }

    @Test
    void findTop20ByAccountEntityOrderByCreatedDesc(){
        List<TransactionEntity> transactionEntities = transactionRepository.findTop20ByAccountEntityOrderByCreatedDesc(accountEntity);
        assertEquals(1, transactionEntities.size());
        assertEqualsTransaction(savedEntity, transactionEntities.get(0));
    }

    private void assertEqualsTransaction(TransactionEntity expectedEntity, TransactionEntity actualEntity) {
        assertEquals(expectedEntity.getId(),        actualEntity.getId());
        assertEquals(expectedEntity.getAccountEntity(),   actualEntity.getAccountEntity());
        assertEquals(expectedEntity.getType(), actualEntity.getType());
        assertEquals(expectedEntity.getCreated(),  actualEntity.getCreated());
        assertEquals(expectedEntity.getAmount(),  actualEntity.getAmount());
    }
}
