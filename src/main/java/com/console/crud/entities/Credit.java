package com.console.crud.entities;


import com.console.crud.DTO.CreditDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credits")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Min(value = 0, message = "Amount can't be negative")
    private Double amount;

    //no need for user because we have userId in credit card
    //private Integer userId;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "credit_card_id"
    )
    private CreditCard creditCard;

    @Min(value = 1, message = "Duration should be positive")
    private Integer duration;//in units (depends on rateType field)

    @Min(value = 0, message = "Duration can't be negative")
    private Double interestRate; // monthly/yearly (depends on rateType field)

    @Enumerated(EnumType.STRING)
    @Column(name = "rate_type")
    private RateType rateType; //monthly or yearly

    @Transient
    private Double getTotalSum(){
        return amount * Math.pow(1 + interestRate / 100, duration);
    }

    //TimeStamp:    date of creating a credit

    @Transient
    private Double getMonthPayment(){
        if(interestRate == 0){
            return getTotalSum() / duration;
        } else{
            return getTotalSum() * interestRate /
                    (100 * (Math.pow(1 + interestRate / 100, duration) - 1));
        }
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", amount=" + amount +
                ", creditCard=" + creditCard +
                ", duration=" + duration +
                ", interestRate=" + interestRate + "%" +
                ", rateType=" + rateType +
                ", totalSum=" + getTotalSum() +
                ", monthPayment=" + getMonthPayment() +
                '}';
    }

    public Credit(Double amount, CreditCard creditCard, Integer duration, Double interestRate, RateType rateType) {
        this.amount = amount;
        this.creditCard = creditCard;
        this.duration = duration;
        this.interestRate = interestRate;
        this.rateType = rateType;
    }

    public Credit(CreditDTO credit, CreditCard creditCard){
        this.amount = credit.getAmount();
        this.creditCard = creditCard;
        this.duration = credit.getDuration();
        this.interestRate = credit.getInterestRate();
        this.rateType = credit.getRateType();
    }

    public void setNewValues(CreditDTO credit){
        this.amount = credit.getAmount();
        this.creditCard = creditCard;
        this.duration = credit.getDuration();
        this.interestRate = credit.getInterestRate();
        this.rateType = credit.getRateType();
    }
}
