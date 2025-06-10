package com.console.crud.command.implementations.creditCard;

import com.console.crud.command.BaseCommand;
import com.console.crud.command.Command;
import com.console.crud.services.CreditCardService;
import com.console.crud.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class PrintAllCreditCards extends BaseCommand {

    private final CreditCardService creditCardService;

    public PrintAllCreditCards(CreditCardService creditCardService) {
        super(6, "Type 6 to show all credit cards");
        this.creditCardService = creditCardService;
    }
    @Override
    public void execute() {
        System.out.println(creditCardService.showAll());
    }
}
