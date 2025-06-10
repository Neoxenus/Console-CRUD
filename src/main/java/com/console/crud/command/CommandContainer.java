package com.console.crud.command;

import com.console.crud.command.implementations.Exiting;
import com.console.crud.command.implementations.credit.*;
import com.console.crud.command.implementations.creditCard.*;
import com.console.crud.command.implementations.user.CreateUser;
import com.console.crud.command.implementations.user.DeleteUser;
import com.console.crud.command.implementations.user.PrintAllUsers;
import com.console.crud.command.implementations.user.UpdateUser;
import com.console.crud.services.CreditCardService;
import com.console.crud.services.CreditService;
import com.console.crud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CommandContainer {
    private final Map<Integer, Command> commands;

    @Autowired
    public CommandContainer(List<Command> commandList) {
        commands = commandList.stream()
                .collect(Collectors.toMap(Command::getCode, Function.identity()));
    }

//    public CommandContainer(UserService userService, CreditCardService creditCardService, CreditService creditService) {
//        commands = new HashMap<>();
//        commands.put(new PrintAllUsers(userService).getCode(), new PrintAllUsers(userService));
//        commands.put(new CreateUser(userService).getCode(), new CreateUser(userService));
//        commands.put(new UpdateUser(userService).getCode(), new UpdateUser(userService));
//        commands.put(4, new DeleteUser(userService));
//
//        commands.put(5, new PrintCreditCardsOfUser(creditCardService));
//
//        commands.put(6, new PrintAllCreditCards(creditCardService));
//        commands.put(7, new CreateCreditCard(creditCardService));
//        commands.put(8, new UpdateCreditCard(creditCardService));
//        commands.put(9, new DeleteCreditCard(creditCardService));
//
//        commands.put(10, new PrintAllCredits(creditService));
//        commands.put(11, new CreateCredit(creditService));
//        commands.put(12, new UpdateCredit(creditService));
//        commands.put(13, new DeleteCredit(creditService));
//
//        commands.put(14, new PrintAllCreditsOfUser(creditService));
//
//
//        commands.put(0, new Exiting());
//
//
//    }

    public Command getCommand(Integer command) {
        return commands.get(command);
    }

    public void doCommand(Command command) {
        if(command == null){
            System.out.println("Incorrect command");
            return;
        }
        command.execute();
    }

    public Collection<Command> getAllCommands(){
        return commands.values();
    }
}
