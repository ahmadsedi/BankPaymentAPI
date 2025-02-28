package com.ahmadsedi.ibpts.data.entity;

import java.util.Optional;

/**
 * The {@code TransactionType} represents a type of transaction which is debit or credit.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 12:56
 */

public enum TransactionType {
    DEBIT("debit"), CREDIT("credit");
    String value;

    TransactionType(String value) {
        this.value = value;
    }

    public static Optional<TransactionType> getTransactionType(String value){
        if(DEBIT.value.equalsIgnoreCase(value)){
            return Optional.of(DEBIT);
        }else if(CREDIT.value.equalsIgnoreCase(value)){
            return Optional.of(CREDIT);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
