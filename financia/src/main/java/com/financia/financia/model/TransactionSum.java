package com.financia.financia.model;


import com.financia.financia.model.pkclass.TransactionSumPrimaryKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Immutable
@Table(name = "v_transaction_sum")
@Getter
@IdClass(TransactionSumPrimaryKey.class)
public class TransactionSum {
    @Id
    private Long userId;
    @Id
    private String category;

    private BigDecimal sumCount;

}
