package com.console.crud.command.implementations.creditCard;

import com.console.crud.command.BaseCommand;
import com.console.crud.services.CreditCardService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class PrintCreditCardsOfUser extends BaseCommand {
    private final CreditCardService creditCardService;

    public PrintCreditCardsOfUser(CreditCardService creditCardService) {
        super(5, "Type 5 to show all credit cards of user");
        this.creditCardService = creditCardService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entry user id");
        int id;
        try{
            id = Integer.parseInt(scanner.nextLine());
        } catch (Exception e){
            System.out.println("Error:\nId must be a number");
            return;
        }

        System.out.println(creditCardService.findByUserId(id));
    }
}
