package com.console.crud.command.implementations.credit;

import com.console.crud.DTO.CreditDTO;
import com.console.crud.command.BaseCommand;
import com.console.crud.entities.RateType;
import com.console.crud.services.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateCredit extends BaseCommand {

    private final CreditService creditService;
    @Autowired
    public CreateCredit(CreditService creditService) {
        super(11, "Type 11 to make new credit");
        this.creditService = creditService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter amount for crediting:");
        double amount;
        try{
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error:\nAmount must be a number");
            return;
        }

        System.out.println("Enter credit card id: ");
        int creditCardId;
        try{
            creditCardId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error:\nCredit card id must be a number");
            return;
        }

        System.out.println("Enter duration of credit: ");
        int duration;
        try{
            duration = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error:\nDuration must be a number");
            return;
        }

        System.out.println("Enter interest rate for crediting (in percentage):");
        double interestRate;
        try{
            interestRate = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error:\nInterest rate must be a number");
            return;
        }

        System.out.println("Enter type of crediting ([M/m] for monthly and [Y/y] for yearly payments):");
        RateType rateType =
        switch (scanner.nextLine().toLowerCase()) {
            case "y" -> RateType.YEARLY;
            case "m" -> RateType.MONTHLY;
            default -> null;
        };
        if(rateType == null){
            System.out.println("Error:\nIncorrect input enter y or Y for yearly payments and m or M for monthly");
            return;
        }

        System.out.println(creditService.addCredit(
                new CreditDTO(amount, creditCardId, duration, interestRate, rateType)));
    }
}
