package com.ahmadsedi.ibpts.validation;

import com.ahmadsedi.ibpts.vo.Account;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 05/03/2025
 * Time: 12:15
 */

class AccountValidationTests {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var account =
                new Account();
        account.setBalance(10);
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenBalanceIsZeroThenValidationFails() {
        var account =
                new Account();
        account.setBalance(0);
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The balance must be greater than zero.");
    }

    @Test
    void whenBalanceIsNegativeThenValidationFails() {
        var account =
                new Account();
        account.setBalance(-1);
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The balance must be greater than zero.");
    }
}
