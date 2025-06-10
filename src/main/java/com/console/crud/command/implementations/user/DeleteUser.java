package com.console.crud.command.implementations.user;

import com.console.crud.command.BaseCommand;
import com.console.crud.command.Command;
import com.console.crud.services.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DeleteUser extends BaseCommand {

    private final UserService userService;

    public DeleteUser(UserService userService) {
        super(4, "Type 4 to delete user");
        this.userService = userService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id of user that you want to delete");
        int id;
        try{
            id = scanner.nextInt();
        } catch (Exception e){
            System.out.println("Error:\nId must be a number");
            scanner.nextLine(); //fix of broken scanner
            return;
        }
        scanner.nextLine();

        System.out.println(userService.deleteUser(id));
    }
}
