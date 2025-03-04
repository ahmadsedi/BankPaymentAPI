package com.ahmadsedi.ibpts.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * The {@code TransactionEntity} represents a bank transaction, and is stored in database table transaction_tbl.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 10:47
 */
@Entity
@Table(name = "transaction_tbl")
@Getter
@Setter
@EqualsAndHashCode
public class TransactionEntity {

    @Id
    @GeneratedValue
    @Column(name = "transaction_id")
    private int id;

    private double amount;

    @Enumerated(EnumType.ORDINAL)
    private TransactionType type;

    @CreationTimestamp
    private Date created;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

}
