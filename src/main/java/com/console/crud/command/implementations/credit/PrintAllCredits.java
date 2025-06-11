package com.console.crud.command.implementations.credit;

import com.console.crud.command.BaseCommand;
import com.console.crud.services.CreditService;
import org.springframework.stereotype.Component;

@Component
public class PrintAllCredits extends BaseCommand {

    private final CreditService creditService;

    public PrintAllCredits(CreditService creditService) {
        super(10, "Type 10 to show all credits");
        this.creditService = creditService;
    }

    @Override
    public void execute() {
        System.out.println(creditService.showAll());
    }
}
