package com.console.crud.services;

import com.console.crud.DAO.CreditCardDAO;
import com.console.crud.DAO.CreditDAO;
import com.console.crud.DTO.CreditDTO;
import com.console.crud.entities.Credit;
import com.console.crud.entities.CreditCard;
import com.console.crud.entities.User;
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
public class CreditService {
    private final CreditDAO creditDAO;
    private final CreditCardDAO creditCardDAO;

    private final SmartValidator validator;

    @Autowired
    public CreditService(CreditDAO creditDAO, CreditCardDAO creditCardDAO, SmartValidator validator) {
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
        try{
            creditCard = creditCardDAO.showCreditCardById(creditDTO.getCreditCardId()).get(0);
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
            creditDAO.addCredit(credit);
            return "Added";
        }
    }
    public String updateUser(int id, CreditDTO updatedCredit) {

        String errors = "Error\n";
        boolean isErrors = false;

        Credit credit = null;
        try{
            credit = creditDAO.showCreditById(id).get(0);
        } catch (NoSuchElementException e){
            errors += "No credit with such an id";
            isErrors = true;
        }

        credit.setNewValues(updatedCredit);

        BindingResult bindingResult = new BeanPropertyBindingResult(credit, "credit");
        validator.validate(credit, bindingResult);

        if(bindingResult.hasErrors() || isErrors){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            creditDAO.updateCredit(id, credit);
            return "Updated";
        }
    }

    public String deleteUser(int id){
        String errors = "Error\n";
        if(creditDAO.showCreditById(id).isEmpty()){
            errors += "No credits with such an id\n";
            return errors;
        } else {
            creditDAO.deleteCredit(id);
            return "Deleted";
        }
    }

}
