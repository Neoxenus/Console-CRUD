package com.console.crud.command.implementations.credit;

import com.console.crud.command.BaseCommand;
import com.console.crud.command.Command;
import com.console.crud.services.CreditService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DeleteCredit extends BaseCommand {
    private final CreditService creditService;
    public DeleteCredit(CreditService creditService) {
        super(13, "Type 13 to delete credit");
        this.creditService = creditService;
    }
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id of credit that you want to delete");
        int id;
        try{
            id = scanner.nextInt();
        } catch (Exception e){
            System.out.println("Error:\nId must be a number");
            return;
        } finally {
            scanner.nextLine(); //fix of broken scanner
        }
        System.out.println(creditService.deleteUser(id));
    }


}
