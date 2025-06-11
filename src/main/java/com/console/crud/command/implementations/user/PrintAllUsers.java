package com.console.crud.command.implementations.user;

import com.console.crud.command.BaseCommand;
import com.console.crud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrintAllUsers extends BaseCommand {


    private final UserService userService;

    @Autowired
    public PrintAllUsers(UserService userService) {
        super(1, "Type 1 to show all users");
        this.userService = userService;
    }

    @Override
    public void execute() {
        System.out.println(userService.showAll());
    }
}
