package com.console.crud.services.implementations;

import com.console.crud.DAO.CreditCardDAO;
import com.console.crud.DAO.UserDAO;
import com.console.crud.DTO.CreditCardDTO;
import com.console.crud.entities.CreditCard;
import com.console.crud.entities.User;
import com.console.crud.services.CreditCardService;
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
public class CreditCardServiceImpl implements CreditCardService {


    private final CreditCardDAO creditCardDAO;

    private final UserDAO userDAO;

    private final SmartValidator validator;

    @Autowired
    public CreditCardServiceImpl(CreditCardDAO creditCardDAO, UserDAO userDAO, SmartValidator validator) {
        this.creditCardDAO = creditCardDAO;
        this.userDAO = userDAO;
        this.validator = validator;
    }

    public String showAll(){
        List<CreditCard> creditCards = creditCardDAO.showAll();
        if (!creditCards.isEmpty())
            return creditCards.stream().map(CreditCard::toString).collect(Collectors.joining("\n"));
        else
            return "Nothing to display";
    }

    public String addCreditCard(CreditCardDTO creditCardDTO){
        String errors = "Error\n";
        boolean existsWithNumber = creditCardDAO.showCreditCardByNumber(creditCardDTO.getNumber()).isPresent();
        if(existsWithNumber){
            errors += "Credit card with such an number already exists\n";
        }

        User user;
        try {
            user = userDAO.showUser(creditCardDTO.getUserId()).orElseThrow();
        } catch (NoSuchElementException e){
            errors += "No user with such an id\n";
            return errors;
        }

        CreditCard creditCard = new CreditCard(creditCardDTO, user);

        BindingResult bindingResult = new BeanPropertyBindingResult(creditCard, "credit card");
        validator.validate(creditCard, bindingResult);

        if(bindingResult.hasErrors() || existsWithNumber){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            try {
                creditCardDAO.addCreditCard(creditCard);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }
            return "Added";
        }
    }

    public String updateCreditCard(int id, CreditCardDTO creditCardDTO) {
        String errors = "Error\n";
        boolean existsWithNumber =  creditCardDAO.showCreditCardByNumber(creditCardDTO.getNumber())
                .map(card -> card.getId().equals(id))
                .orElse(true);

        boolean notExistsCreditCard = creditCardDAO.showCreditCardById(id).isEmpty();

        if(existsWithNumber)
            errors += "Credit card with such an number already exists\n";
        if(notExistsCreditCard)
            errors += "No credit card with such an id\n";

        User user;
        try {
            user = userDAO.showUser(creditCardDTO.getUserId()).orElseThrow();
        } catch (NoSuchElementException e){
            errors += "No user with such an id\n";
            return errors;
        }
        CreditCard creditCard = new CreditCard(creditCardDTO, user);

        BindingResult bindingResult = new BeanPropertyBindingResult(creditCard, "credit card");
        validator.validate(creditCard, bindingResult);

        if(bindingResult.hasErrors() || existsWithNumber  || notExistsCreditCard){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            try {
                creditCardDAO.updateCreditCard(id, creditCard);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }
            return "Updated";
        }
    }

    public String deleteCreditCard(int id){
        String errors = "Error\n";
        if(creditCardDAO.showCreditCardById(id).isEmpty()){
            errors += "No credit card with such id\n";
            return errors;
        } else {
            try {
                creditCardDAO.deleteCreditCard(id);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }
            return "Deleted";
        }
    }

    public String findByUserId(int userId){
        String errors = "Error\n";
        if(userDAO.showUser(userId).isEmpty()){
            errors += "No users with such id\n";
            return errors;
        } else {
            List<CreditCard> creditCards = creditCardDAO.findByUserId(userId);
            if (!creditCards.isEmpty())
                return creditCards.stream().map(CreditCard::toString).collect(Collectors.joining("\n"));
            else
                return "Nothing to display";

        }
    }
}
