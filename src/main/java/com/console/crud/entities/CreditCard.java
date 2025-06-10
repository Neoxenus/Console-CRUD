package com.console.crud.entities;


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
    @Column(name = "number")
    private String number;//it`s also unique. Check for uniqueness is manual with DAO

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

    @Column(name = "user_id")
//    @ManyToOne
//    @JoinColumn(
//            nullable = false,
//            name = "user_id"
//    )
    private Integer user_id;

    public CreditCard(String number, String cvv, String expireDate, Integer user_id) {
        this.number = number;
        this.cvv = cvv;
        this.expireDate = expireDate;
        balance = 0;
        this.user_id = user_id;
    }
}
