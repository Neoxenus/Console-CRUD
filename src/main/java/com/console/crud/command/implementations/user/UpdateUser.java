package com.console.crud.command.implementations.user;

import com.console.crud.DTO.UserDTO;
import com.console.crud.command.BaseCommand;
import com.console.crud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UpdateUser extends BaseCommand {

    private final UserService userService;

    @Autowired
    public UpdateUser( UserService userService) {
        super(3, "Type 3 to update user");
        this.userService = userService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter id of user to update");

        int id;
        try{
            id = Integer.parseInt(scanner.nextLine());
        } catch (Exception e){
            System.out.println("Error:\nId must be a number");
            return;
        }


        System.out.println("Enter user name: ");
        String name = scanner.nextLine();

        System.out.println("Enter user surname: ");
        String surname = scanner.nextLine();

        System.out.println("Enter user age: ");
        int age;
        try{
            age = Integer.parseInt(scanner.nextLine());
        } catch (Exception e){
            System.out.println("Age must be a number");
            return;
        }

        System.out.println("Enter user email: ");
        String email = scanner.nextLine();


        System.out.println(userService.updateUser(id, new UserDTO(name, surname, age, email)));
    }
}
