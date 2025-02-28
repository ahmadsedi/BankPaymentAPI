package com.ahmadsedi.ibpts.data.repo;

import com.ahmadsedi.ibpts.data.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * The {@code AccountRepository} handles account creation or query operation.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 11:05
 */

public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {
}
