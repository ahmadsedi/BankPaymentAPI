package com.ahmadsedi.ibpts;

import com.ahmadsedi.ibpts.data.entity.AccountEntity;
import com.ahmadsedi.ibpts.data.entity.TransactionType;
import com.ahmadsedi.ibpts.data.repo.AccountRepository;
import com.ahmadsedi.ibpts.data.repo.TransactionRepository;
import com.ahmadsedi.ibpts.vo.Account;
import com.ahmadsedi.ibpts.vo.TransferRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 11:16
 */

@SpringBootTest(webEnvironment = RANDOM_PORT)
class AccountEndpointTests {

    @Autowired
    private WebTestClient client;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setupDb() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void createAccount_validInput_okExpected() {
        assertEquals(0, accountRepository.count());
        double balance = 10;
        postAndVerifyAccountCreation(balance, HttpStatus.OK);
    }

    @Test
    void createAccount_invalidBalance_badRequestExpected() {
        assertEquals(0, accountRepository.count());
        double balance = -1;
        postAndVerifyAccountCreation(balance, HttpStatus.BAD_REQUEST);
        assertEquals(0, accountRepository.count());
    }

    @Test
    void getBalance_validAccountId_okExpecte() {
        double balance = 10;
        AccountEntity newAccountEntity = new AccountEntity();
        newAccountEntity.setBalance(balance);
        newAccountEntity = accountRepository.save(newAccountEntity);
        getAndVerifyAccountBalanceById(String.valueOf(newAccountEntity.getId()), HttpStatus.OK)
                .jsonPath("$.balance").isEqualTo(balance)
                .jsonPath("$.accountId").isEqualTo(newAccountEntity.getId());
    }

    @Test
    void getBalance_notExistAccountId_badRequestExpected() {
        String accountId = String.valueOf(Integer.MAX_VALUE);
        getAndVerifyAccountBalanceById(accountId, HttpStatus.BAD_REQUEST)
                .jsonPath("$.message").isEqualTo("Invalid account details, accountId: " + accountId)
                .jsonPath("$.path").isEqualTo("/accounts/" + accountId + "/balance")
                .jsonPath("$.timestamp").isNotEmpty()
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void getBalance_negativeAccountId_badRequestExpected() {
        String accountId = String.valueOf(-1);
        getAndVerifyAccountBalanceById(accountId, HttpStatus.BAD_REQUEST)
                .jsonPath("$.message").isEqualTo("Invalid account details, accountId: " + accountId)
                .jsonPath("$.path").isEqualTo("/accounts/" + accountId + "/balance")
                .jsonPath("$.timestamp").isNotEmpty()
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void transfer_validValue_okExpected() {
        double transferAmount = 10;
        AccountEntity sourceAccountEntity = new AccountEntity();
        sourceAccountEntity.setBalance(10);
        sourceAccountEntity = accountRepository.save(sourceAccountEntity);

        AccountEntity destAccountEntity = new AccountEntity();
        destAccountEntity.setBalance(20);
        destAccountEntity = accountRepository.save(destAccountEntity);
        postAndVerifyTransfer(transferAmount, sourceAccountEntity.getId(), destAccountEntity.getId(), HttpStatus.OK)
                .jsonPath("$.sourceTransactionId").isNotEmpty()
                .jsonPath("$.destTransactionId").isNotEmpty()
                .jsonPath("$.amount").isEqualTo(transferAmount);

        assertEquals(0, accountRepository.findById(sourceAccountEntity.getId()).get().getBalance());
        assertEquals(30, accountRepository.findById(destAccountEntity.getId()).get().getBalance());
    }

    @Test
    void transfer_negativeAmount_badRequestExpected() {
        double negativeAmount = -1;
        AccountEntity sourceAccountEntity = new AccountEntity();
        sourceAccountEntity.setBalance(10);
        sourceAccountEntity = accountRepository.save(sourceAccountEntity);

        AccountEntity destAccountEntity = new AccountEntity();
        destAccountEntity.setBalance(20);
        destAccountEntity = accountRepository.save(destAccountEntity);
        postAndVerifyTransfer(negativeAmount, sourceAccountEntity.getId(), destAccountEntity.getId(), HttpStatus.BAD_REQUEST)
                .jsonPath("$.message").isEqualTo("{amount:The amount must be greater than zero.}")
                .jsonPath("$.path").isEqualTo("/accounts/transfer")
                .jsonPath("$.timestamp").isNotEmpty()
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value());

        assertEquals(10, accountRepository.findById(sourceAccountEntity.getId()).get().getBalance());
        assertEquals(20, accountRepository.findById(destAccountEntity.getId()).get().getBalance());
    }

    @Test
    void transfer_zeroAmount_badRequestExpected() {
        AccountEntity sourceAccountEntity = new AccountEntity();
        sourceAccountEntity.setBalance(10);
        sourceAccountEntity = accountRepository.save(sourceAccountEntity);

        AccountEntity destAccountEntity = new AccountEntity();
        destAccountEntity.setBalance(20);
        destAccountEntity = accountRepository.save(destAccountEntity);

        double zeroAmount = 0;
        postAndVerifyTransfer(zeroAmount, sourceAccountEntity.getId(), destAccountEntity.getId(), HttpStatus.BAD_REQUEST)
                .jsonPath("$.message").isEqualTo("{amount:The amount must be greater than zero.}")
                .jsonPath("$.path").isEqualTo("/accounts/transfer")
                .jsonPath("$.timestamp").isNotEmpty()
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value());

        assertEquals(10, accountRepository.findById(sourceAccountEntity.getId()).get().getBalance());
        assertEquals(20, accountRepository.findById(destAccountEntity.getId()).get().getBalance());
    }

    @Test
    void transfer_invalidSourceAccountId_badRequestExpected() {
        int sourceAccountId = Integer.MAX_VALUE;

        AccountEntity destAccountEntity = new AccountEntity();
        destAccountEntity.setBalance(20);
        destAccountEntity = accountRepository.save(destAccountEntity);

        double zeroAmount = 10;
        postAndVerifyTransfer(zeroAmount, sourceAccountId, destAccountEntity.getId(), HttpStatus.BAD_REQUEST)
                .jsonPath("$.message").isEqualTo("No account found with accountId: "+String.valueOf(sourceAccountId))
                .jsonPath("$.path").isEqualTo("/accounts/transfer")
                .jsonPath("$.timestamp").isNotEmpty()
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value());

        assertEquals(20, accountRepository.findById(destAccountEntity.getId()).get().getBalance());
    }

    @Test
    void transfer_invalidDestAccountId_badRequestExpected() {
        AccountEntity sourceAccountEntity = new AccountEntity();
        sourceAccountEntity.setBalance(10);
        sourceAccountEntity = accountRepository.save(sourceAccountEntity);

        int destAccountId = Integer.MAX_VALUE;

        double zeroAmount = 10;
        postAndVerifyTransfer(zeroAmount, sourceAccountEntity.getId(), destAccountId, HttpStatus.BAD_REQUEST)
                .jsonPath("$.message").isEqualTo("No account found with accountId: "+String.valueOf(destAccountId))
                .jsonPath("$.path").isEqualTo("/accounts/transfer")
                .jsonPath("$.timestamp").isNotEmpty()
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value());

        assertEquals(10, accountRepository.findById(sourceAccountEntity.getId()).get().getBalance());
    }

    @Test
    void miniReport_validData_okExpected() {
        double transferAmount = 10;
        AccountEntity sourceAccountEntity = new AccountEntity();
        sourceAccountEntity.setBalance(10);
        sourceAccountEntity = accountRepository.save(sourceAccountEntity);

        AccountEntity destAccountEntity = new AccountEntity();
        destAccountEntity.setBalance(20);
        destAccountEntity = accountRepository.save(destAccountEntity);
        postAndVerifyTransfer(transferAmount, sourceAccountEntity.getId(), destAccountEntity.getId(), HttpStatus.OK);
        getAndVerifyMiniReport(String.valueOf(sourceAccountEntity.getId()), HttpStatus.OK)
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].id").isNotEmpty()
                .jsonPath("$[0].transaction-date").isNotEmpty()
                .jsonPath("$[0].amount").isEqualTo(transferAmount)
                .jsonPath("$[0].type").isEqualTo(TransactionType.DEBIT.toString())
                .jsonPath("$[0].account-id").isEqualTo(sourceAccountEntity.getId());
    }

    @Test
    void miniReport_nonPositiveAccountId_badRequestExpected() {
        int accountId = 0;

        getAndVerifyMiniReport(String.valueOf(accountId), HttpStatus.BAD_REQUEST)
                .jsonPath("$.message").isEqualTo("Invalid account details, accountId: "+String.valueOf(accountId))
                .jsonPath("$.path").isEqualTo("/accounts/"+accountId+"/statements/mini")
                .jsonPath("$.timestamp").isNotEmpty()
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void miniReport_notExistAccountId_badRequestExpected() {
        int accountId = Integer.MAX_VALUE;

        getAndVerifyMiniReport(String.valueOf(accountId), HttpStatus.BAD_REQUEST)
                .jsonPath("$.message").isEqualTo("Invalid account details, accountId: "+String.valueOf(accountId))
                .jsonPath("$.path").isEqualTo("/accounts/"+accountId+"/statements/mini")
                .jsonPath("$.timestamp").isNotEmpty()
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private WebTestClient.BodyContentSpec postAndVerifyTransfer(double amount, int sourceAccountId,
                                                                int destAccountId,
                                                                HttpStatus expectedStatus) {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setAmount(amount);
        transferRequest.setSourceAccountId(sourceAccountId);
        transferRequest.setDestAccountId(destAccountId);
        return client.post()
                .uri("/accounts/transfer")
                .body(just(transferRequest), TransferRequest.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

    private void postAndVerifyAccountCreation(double balance, HttpStatus expectedStatus) {
        Account account = new Account();
        account.setBalance(balance);
        client.post()
                .uri("/accounts")
                .body(just(account), Account.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }

    private WebTestClient.BodyContentSpec getAndVerifyAccountBalanceById(String accountId, HttpStatus expectedStatus) {
        return client.get()
                .uri("/accounts/" + accountId + "/balance")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

    private WebTestClient.BodyContentSpec getAndVerifyMiniReport(String accountId, HttpStatus expectedStatus) {
        return client.get()
                .uri("/accounts/" + accountId + "/statements/mini")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }
}
