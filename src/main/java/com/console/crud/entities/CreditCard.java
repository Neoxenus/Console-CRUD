package com.console.crud.entities;


import com.console.crud.DTO.CreditCardDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credit_cards")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(min = 16, max = 16, message = "Credit card number is exactly 16 integers. Incorrect size.")
    @Pattern(regexp = "\\d{16}", message = "Number should be 16 digits. Incorrect format")
    @Column(name = "number", unique = true)
    private String number;

    @Size(min = 3, max = 3, message = "CVV should be exactly 3 integers")
    @Pattern(regexp = "\\d{3}", message = "CVV should be exactly 3 integers")
    @Column(name = "cvv")
    private String cvv;

    @Size(min = 6, max = 7, message = "Expire date should be entered in the following format: m-yyyy, or mm-yyyy. Incorrect size")
    @Pattern(regexp = "^(0?[1-9]|1[0-2])-\\d{4}$",
            message = "Expire date should be entered in the following format: m-yyyy, or mm-yyyy. Incorrect format")
    @Column(name = "expire_date")
    private String expireDate;

    @Column(name = "balance")
    private double balance;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    public CreditCard(CreditCardDTO creditCard, User user){
        this.number = creditCard.getNumber();
        this.cvv = creditCard.getCvv();
        this.expireDate = creditCard.getExpireDate();
        this.balance = creditCard.getBalance();
        this.user = user;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", cvv='" + cvv + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", balance=" + balance +
                ", userId=" + user.getId() +
                '}';
    }
}
