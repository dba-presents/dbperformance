package com.dbapresents.dbperformance.transaction;

public enum TransactionStatusWithId {
    INVALID(1), CANCELLED(2), AUTH_DECLINED(3), AUTHORIZED(4), REFUND(5), PAYMENT_REQUESTED(6);

    private final Integer id;

    TransactionStatusWithId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
