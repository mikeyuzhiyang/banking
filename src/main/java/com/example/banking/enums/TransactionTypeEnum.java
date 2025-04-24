package com.example.banking.enums;

import lombok.Getter;

@Getter
public enum TransactionTypeEnum {

    DEPOSIT(0,"存款"),
    WITHDRAWAL(1, "取款"),
    TRANSFER(2, "转账");

    final int code;

    final String description;

    TransactionTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
