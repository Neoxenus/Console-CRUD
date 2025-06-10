package com.console.crud.command.implementations;

import com.console.crud.command.BaseCommand;
import com.console.crud.command.Command;
import org.springframework.stereotype.Component;

@Component
public class Exiting extends BaseCommand {
    public Exiting() {
        super(0, "Type 0 to exit");
    }

    @Override
    public void execute() {
        System.out.println("Exiting application...");
        System.exit(0);
    }
}
