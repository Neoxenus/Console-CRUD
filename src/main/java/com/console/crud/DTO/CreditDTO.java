package com.console.crud.DTO;

import com.console.crud.entities.CreditCard;
import com.console.crud.entities.RateType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
