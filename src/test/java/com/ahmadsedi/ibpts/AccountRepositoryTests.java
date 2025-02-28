package com.ahmadsedi.ibpts;

import com.ahmadsedi.ibpts.data.entity.AccountEntity;
import com.ahmadsedi.ibpts.data.repo.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 16:41
 */
@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTests {

    @Autowired
    AccountRepository accountRepository;

    AccountEntity savedEntity;

    @BeforeEach
    void setupDb() {
        accountRepository.deleteAll();
        AccountEntity entity = new AccountEntity();
        entity.setBalance(5);
        savedEntity = accountRepository.save(entity);
        assertEqualsAccount(entity, savedEntity);
    }

    @Test
    void create() {
        AccountEntity newAccountEntity = new AccountEntity();
        newAccountEntity.setBalance(10);
        newAccountEntity = accountRepository.save(newAccountEntity);

        AccountEntity foundEntity = accountRepository.findById(newAccountEntity.getId()).get();
        assertEqualsAccount(newAccountEntity, foundEntity);

        assertEquals(2, accountRepository.count());
    }

    @Test
    void findById() {
        AccountEntity foundEntity = accountRepository.findById(savedEntity.getId()).get();
        assertEqualsAccount(savedEntity, foundEntity);
    }

    private void assertEqualsAccount(AccountEntity expectedEntity, AccountEntity actualEntity) {
        assertEquals(expectedEntity.getId(),        actualEntity.getId());
        assertEquals(expectedEntity.getVersion(),   actualEntity.getVersion());
        assertEquals(expectedEntity.getBalance(), actualEntity.getBalance());
        assertEquals(expectedEntity.getCreated(),  actualEntity.getCreated());
    }
}
