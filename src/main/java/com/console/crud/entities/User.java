package com.console.crud.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @NotEmpty(message = "Name can`t be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 symbols")
    private String name;
    @NotEmpty(message = "Surname can`t be empty")
    @Size(min = 2, max = 30, message = "Surname should be between 2 and 30 symbols")
    private String surname;

    @Min(value = 0, message = "Age can`t be negative")
    @Max(value = 200, message = "Age must be real")
    private Integer age;

    @NotEmpty(message = "Email can`t be empty")
    @Email(message = "Email should be valid")
    private String email;//it`s also unique. Check for uniqueness is manual with DAO

    public User(String name, String surname, Integer age, String email) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
    }
}
