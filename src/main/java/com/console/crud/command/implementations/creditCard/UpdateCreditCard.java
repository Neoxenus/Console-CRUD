package com.console.crud.command.implementations.creditCard;

import com.console.crud.DTO.CreditCardDTO;
import com.console.crud.command.BaseCommand;
import com.console.crud.services.CreditCardService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UpdateCreditCard extends BaseCommand {
    private final CreditCardService creditCardService;

    public UpdateCreditCard(CreditCardService creditCardService) {
        super(8, "Type 8 to update credit card");
        this.creditCardService = creditCardService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id of card to update");
        int id;
        try{
            id = Integer.parseInt(scanner.nextLine());
        }catch (Exception e){
            System.out.println("Error:\nId must be a number");
            return;
        }

        System.out.println("Enter card number: ");
        String number = scanner.nextLine();

        System.out.println("Enter user cvv: ");
        String cvv = scanner.nextLine();

        System.out.println("Enter card expire date: ");
        String expireDate = scanner.nextLine();

        System.out.println("Enter user id: ");
        int userId;
        try{
            userId = Integer.parseInt(scanner.nextLine());
        } catch (Exception e){
            System.out.println("Error:\nId must be a number");
            return;
        }

        CreditCardDTO creditCard = new CreditCardDTO(number, cvv, expireDate, 0d, userId);

        System.out.println(creditCardService.updateCreditCard(id, creditCard));
    }
}
