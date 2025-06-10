package com.console.crud.command.implementations.creditCard;

import com.console.crud.DAO.CreditCardDAO;
import com.console.crud.command.BaseCommand;
import com.console.crud.command.Command;
import com.console.crud.entities.CreditCard;
import com.console.crud.services.CreditCardService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateCreditCard extends BaseCommand {
    private final CreditCardService creditCardService;

    public CreateCreditCard(CreditCardService creditCardService){
        super(7, "Type 7 to create new credit card");
        this.creditCardService = creditCardService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter card number (16 numbers): ");
        String number = scanner.nextLine();

        System.out.println("Enter user cvv (3 numbers): ");
        String cvv = scanner.nextLine();

        System.out.println("Enter card expire date (format: mm-yyyy or m-yyyy): ");
        String expireDate = scanner.nextLine();

        System.out.println("Enter user id: ");
//                        scanner.nextInt();
        int userId;
        try{
            userId = scanner.nextInt();
        }catch (Exception e){
            System.out.println("Error:\nId must be a number");
            scanner.nextLine(); //fix of broken scanner
            return;
        }

        scanner.nextLine();
        CreditCard creditCard = new CreditCard(number, cvv, expireDate, userId);

        System.out.println(creditCardService.addCreditCard(creditCard));
    }
}
