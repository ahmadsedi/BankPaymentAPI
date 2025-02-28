package com.ahmadsedi.ibpts.data.repo;

import com.ahmadsedi.ibpts.data.entity.AccountEntity;
import com.ahmadsedi.ibpts.data.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The {@code TransactionRepository} handles transaction creation or query operation.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 11:05
 */

public interface TransactionRepository extends CrudRepository<TransactionEntity, Integer> {
    /**
     * finds at most 20 record of most recent transactions of an account, ordered by date descending.
     *
     * @param accountEntity is the account, the query is executed based on that.
     * @return a list of transactions of the account.
     */
    List<TransactionEntity> findTop20ByAccountEntityOrderByCreatedDesc(AccountEntity accountEntity);
}
