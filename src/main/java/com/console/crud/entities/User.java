package com.console.crud.entities;


import com.console.crud.DTO.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @NotEmpty(message = "Name can`t be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 symbols")
    @Column(name="name")
    private String name;
    @NotEmpty(message = "Surname can`t be empty")
    @Size(min = 2, max = 30, message = "Surname should be between 2 and 30 symbols")
    @Column(name="surname")
    private String surname;

    @Min(value = 0, message = "Age can`t be negative")
    @Max(value = 200, message = "Age must be real")
    @Column(name="age")
    private Integer age;

    @NotEmpty(message = "Email can`t be empty")
    @Email(message = "Email should be valid")
    @Column(name="email", unique = true)
    private String email;
    

    public User(UserDTO user){
        this.name = user.getName();
        this.surname = user.getSurname();
        this.age = user.getAge();
        this.email = user.getEmail();
    }
}
