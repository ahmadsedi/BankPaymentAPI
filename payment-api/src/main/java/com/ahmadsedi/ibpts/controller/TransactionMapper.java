package com.ahmadsedi.ibpts.controller;

import com.ahmadsedi.ibpts.data.entity.TransactionEntity;
import com.ahmadsedi.ibpts.vo.Transaction;
import org.springframework.stereotype.Component;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 16:13
 *
 * The {@code TransactionMapper} provides utility method to convert business object {@code TransactionEntity} to
 * {@code Transaction} object which serialized as a json object into HTTP response.
 *
 */
@Component
public class TransactionMapper {
    /**
     * Converts entity TransactionEntity to value object Transaction.
     *
     * @param transactionEntity, the transaction entity to be converted.
     * @return corresponding Transaction value object
     */
    public Transaction entityToApi(TransactionEntity transactionEntity) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(transactionEntity.getAccountEntity().getId());
        transaction.setTransactionDate(transactionEntity.getCreated().toString());
        transaction.setAmount(transactionEntity.getAmount());
        transaction.setType(transactionEntity.getType().toString());
        transaction.setId(transactionEntity.getId());
        return transaction;
    }
}
