package com.console.crud.DTO;

import com.console.crud.entities.RateType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreditDTO {

    private Double amount;

    private Integer creditCardId;

    private Integer duration;

    private Double interestRate;

    private RateType rateType;

}
