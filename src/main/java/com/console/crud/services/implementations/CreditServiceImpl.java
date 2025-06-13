package com.console.crud.services.implementations;

import com.console.crud.DAO.CreditCardDAO;
import com.console.crud.DAO.CreditDAO;
import com.console.crud.DTO.CreditDTO;
import com.console.crud.entities.Credit;
import com.console.crud.entities.CreditCard;
import com.console.crud.services.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CreditServiceImpl implements CreditService {
    private final CreditDAO creditDAO;
    private final CreditCardDAO creditCardDAO;

    private final SmartValidator validator;

    @Autowired
    public CreditServiceImpl(CreditDAO creditDAO, CreditCardDAO creditCardDAO, SmartValidator validator) {
        this.creditDAO = creditDAO;
        this.creditCardDAO = creditCardDAO;
        this.validator = validator;
    }


    public String showAll(){
        List<Credit> credits = creditDAO.showAll();
        if (!credits.isEmpty())
            return credits.stream().map(Credit::toString).collect(Collectors.joining("\n"));
        else
            return "No credits to display";
    }

    public String showAllByUserId(int userId){
        List<Credit> credits = creditDAO.showCreditsByUserId(userId);
        if (!credits.isEmpty())
            return credits.stream().map(Credit::toString).collect(Collectors.joining("\n"));
        else
            return "No credits to display";
    }

    public String addCredit(CreditDTO creditDTO){
        String errors = "Error\n";
        boolean isErrors = false;

        CreditCard creditCard = null;

        try {
            creditCard = creditCardDAO.showCreditCardById(creditDTO.getCreditCardId()).orElseThrow();
        } catch (NoSuchElementException e){
            errors += "No credit card with such an id";
            isErrors = true;
        }

        Credit credit = new Credit(creditDTO, creditCard);

        BindingResult bindingResult = new BeanPropertyBindingResult(credit, "credit");
        validator.validate(credit, bindingResult);

        if(bindingResult.hasErrors() || isErrors){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            try {
                creditDAO.addCredit(credit);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }

            return "Added";
        }
    }
    public String updateCredit(int id, CreditDTO updatedCredit) {
        String errors = "Error\n";

        if(creditDAO.showCreditById(id).isEmpty()){
            errors += "No credit with such an id";
            return errors;
        }

        CreditCard creditCard;
        try {
            creditCard = creditCardDAO.showCreditCardById(updatedCredit.getCreditCardId()).orElseThrow();
        } catch (NoSuchElementException e){
            errors += "No credit card with such an id";
            return errors;
        }
        Credit credit = new Credit(updatedCredit, creditCard);

        BindingResult bindingResult = new BeanPropertyBindingResult(credit, "credit");
        validator.validate(credit, bindingResult);

        if(bindingResult.hasErrors()){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            try {
                creditDAO.updateCredit(id, credit);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }

            return "Updated";
        }
    }

    public String deleteCredit(int id){
        String errors = "Error\n";
        if(creditDAO.showCreditById(id).isEmpty()){
            errors += "No credits with such an id\n";
            return errors;
        } else {
            try {
                creditDAO.deleteCredit(id);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }

            return "Deleted";
        }
    }

}
