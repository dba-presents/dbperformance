package com.dbapresents.dbperformance.transaction;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private TransactionStatusWithId statusWithId;

}
