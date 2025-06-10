package com.console.crud.command.implementations.credit;

import com.console.crud.DTO.CreditDTO;
import com.console.crud.command.BaseCommand;
import com.console.crud.command.Command;
import com.console.crud.entities.RateType;
import com.console.crud.services.CreditService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UpdateCredit extends BaseCommand {
    private final CreditService creditService;
    public UpdateCredit(CreditService creditService) {
        super(12, "Type 12 to update credit");
        this.creditService = creditService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter credit id: ");
        int creditId;
        try{
            creditId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error:\nCredit id must be a number");
            return;
        }

        System.out.println("Enter new amount for crediting:");
        double amount;
        try{
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error:\nAmount must be a number");
            return;
        }

        System.out.println("Enter new credit card id: ");
        int creditCardId;
        try{
            creditCardId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error:\nCredit card id must be a number");
            return;
        }

        System.out.println("Enter new duration of credit: ");
        int duration;
        try{
            duration = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error:\nDuration must be a number");
            return;
        }

        System.out.println("Enter new interest rate for crediting (in percentage):");
        double interestRate;
        try{
            interestRate = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error:\nInterest rate must be a number");
            return;
        }

        System.out.println("Enter new type of crediting ([M/m] for monthly and [Y/y] for yearly payments):");
        RateType rateType;
        try{
            String typeCode = scanner.nextLine().toLowerCase();
            if(typeCode.equals("y")){
                rateType = RateType.YEARLY;
            } else if (typeCode.equals("m")) {
                rateType = RateType.MONTHLY;
            } else{
                throw new IllegalStateException();
            }
        } catch (Exception e){
            System.out.println("Error:\nIncorrect input enter y or Y for yearly payments and m or M for monthly");
            return;
        }

        System.out.println(creditService.updateUser(creditId,
                new CreditDTO(amount, creditCardId, duration, interestRate, rateType)));
    }
}
