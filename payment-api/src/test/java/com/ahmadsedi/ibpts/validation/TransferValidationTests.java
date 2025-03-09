package com.ahmadsedi.ibpts.validation;

import com.ahmadsedi.ibpts.vo.TransferRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 05/03/2025
 * Time: 12:23
 */

class TransferValidationTests {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var transferRequest =
                new TransferRequest();
        transferRequest.setAmount(10);
        Set<ConstraintViolation<TransferRequest>> violations = validator.validate(transferRequest);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenAmountIsZeroThenValidationFails() {
        var transferRequest =
                new TransferRequest();
        transferRequest.setAmount(0);
        Set<ConstraintViolation<TransferRequest>> violations = validator.validate(transferRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The amount must be greater than zero.");
    }

    @Test
    void whenAmountIsNegativeThenValidationFails() {
        var transferRequest =
                new TransferRequest();
        transferRequest.setAmount(-1);
        Set<ConstraintViolation<TransferRequest>> violations = validator.validate(transferRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The amount must be greater than zero.");
    }
}
