package com.console.crud.command.implementations.creditCard;

import com.console.crud.command.BaseCommand;
import com.console.crud.command.Command;
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
            id = scanner.nextInt();
        }catch (Exception e){
            System.out.println("Error:\nId must be a number");
            scanner.nextLine(); //fix of broken scanner
            return;
        }
        scanner.nextLine();

        System.out.println(creditCardService.deleteCreditCard(id));
//                        System.out.println("Deleted");
    }
}
