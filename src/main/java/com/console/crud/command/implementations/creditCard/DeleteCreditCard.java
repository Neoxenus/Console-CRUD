package com.console.crud.command.implementations.creditCard;

import com.console.crud.command.BaseCommand;
import com.console.crud.services.CreditCardService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DeleteCreditCard extends BaseCommand {
    private final CreditCardService creditCardService;

    public DeleteCreditCard(CreditCardService creditCardService) {
        super(9, "Type 9 to delete credit card");
        this.creditCardService = creditCardService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entry id of credit card that you want to delete");
        int id;
        try{
            id = Integer.parseInt(scanner.nextLine());
        }catch (Exception e){
            System.out.println("Error:\nId must be a number");
            return;
        }

        System.out.println(creditCardService.deleteCreditCard(id));
//                        System.out.println("Deleted");
    }
}
