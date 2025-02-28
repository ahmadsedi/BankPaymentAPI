package com.ahmadsedi.ibpts;

import com.ahmadsedi.ibpts.controller.TransactionMapper;
import com.ahmadsedi.ibpts.data.entity.AccountEntity;
import com.ahmadsedi.ibpts.data.entity.TransactionEntity;
import com.ahmadsedi.ibpts.data.entity.TransactionType;
import com.ahmadsedi.ibpts.vo.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 16:28
 */

public class TransactionMapperTests {

    TransactionMapper transactionMapper = new TransactionMapper();

    @Test
    public void entityToApiTest(){
        TransactionEntity entity = new TransactionEntity();
        entity.setAmount(10);
        entity.setCreated(new Date());
        entity.setType(TransactionType.DEBIT);
        entity.setId(1);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(5);
        entity.setAccountEntity(accountEntity);
        Transaction transaction = transactionMapper.entityToApi(entity);
        Assertions.assertEquals(entity.getAmount(), transaction.getAmount(), "Amount not converted.");
        Assertions.assertEquals(entity.getCreated().toString(), transaction.getTransactionDate(), "creation date not converted.");
        Assertions.assertEquals(entity.getType().toString(), transaction.getType(), "Transaction Type not converted.");
        Assertions.assertEquals(entity.getId(), transaction.getId(), "Transaction id not converted.");
        Assertions.assertEquals(entity.getAccountEntity().getId(), transaction.getAccountId(), "AccountId not converted.");
    }
}
