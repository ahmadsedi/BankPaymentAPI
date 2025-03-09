package com.ahmadsedi.ibpts.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.util.Date;

/**
 * The {@code AccountEntity} represent a bank account, and is stored in database table account_tbl.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 10:47
 */

@Entity
@Table(name = "account_tbl")
@Getter@Setter
@EqualsAndHashCode
public class AccountEntity {

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private int id;

    private double balance;

    @Version
    private int version;

    @CreationTimestamp
    private Date created;

}
