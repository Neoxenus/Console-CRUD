package com.console.crud;

import com.console.crud.command.Command;
import com.console.crud.command.CommandContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Scanner;

@Component
public class Controller {

    private final CommandContainer commandContainer;

    @Autowired
    public Controller(CommandContainer commandContainer) {
        this.commandContainer = commandContainer;
    }

    public void mainLoop(){

        printMenu();
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("Type number of command: ");

            int commandCode;
            try{
                commandCode = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e){
                System.out.println("Incorrect command");
                continue;
            }

            try {
                commandContainer.doCommand(commandContainer.getCommand(commandCode));
            } catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Incorrect input");
            }
        }
    }
    private void printMenu() {
        commandContainer.getAllCommands().stream()
                .sorted(Comparator.comparingInt(Command::getCode))
                .forEach(cmd -> System.out.println(cmd.getDescription()));
    }
}
