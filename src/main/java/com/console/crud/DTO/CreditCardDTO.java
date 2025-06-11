package com.console.crud.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreditCardDTO {

    private String number;

    private String cvv;

    private String expireDate;

    private Double balance;

    private Integer userId;
}
