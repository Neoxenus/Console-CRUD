package com.console.crud.command.implementations.user;

import com.console.crud.command.BaseCommand;
import com.console.crud.command.Command;
import com.console.crud.entities.User;
import com.console.crud.services.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateUser extends BaseCommand {
    private final UserService userService;

    public CreateUser(UserService userService) {
        super(2, "Type 2 to save new user");
        this.userService = userService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user name: ");
        String name = scanner.nextLine();

        System.out.println("Enter user surname: ");
        String surname = scanner.nextLine();

        System.out.println("Enter user age: ");
        int age;
        try{
            age = scanner.nextInt();
        } catch (Exception e){
            System.out.println("Error:\nAge must be number");
            return;
        } finally {
            scanner.nextLine(); //fix of broken scanner
        }
        System.out.println("Enter user email: ");


        String email = scanner.nextLine();
        User user = new User(name, surname, age, email);

        System.out.println(userService.addUser(user));
    }
}
