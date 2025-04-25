package com.example.banking.enums;

import lombok.Getter;

@Getter
public enum TransactionTypeEnum {

    DEPOSIT(0,"DEPOSIT"),
    WITHDRAWAL(1, "WITHDRAW"),
    TRANSFER(2, "TRANSFER");

    final int code;

    final String description;

    TransactionTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static String getDescriptionByCode(int code) {
        for (TransactionTypeEnum typeEnum : TransactionTypeEnum.values()) {
            if (typeEnum.code == code) {
                return typeEnum.getDescription();
            }
        }
        return "";
    }
}
