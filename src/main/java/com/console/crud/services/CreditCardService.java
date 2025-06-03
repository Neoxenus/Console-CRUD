package com.console.crud.services;

import com.console.crud.DAO.CreditCardDAO;
import com.console.crud.DAO.UserDAO;
import com.console.crud.entities.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditCardService {

//    @Qualifier(value = "CreditCardDaoJdbcImplementation")
    private final CreditCardDAO creditCardDAO;

    private final UserDAO userDAO;

    private final SmartValidator validator;

    @Autowired
    public CreditCardService(CreditCardDAO creditCardDAO, UserDAO userDAO, SmartValidator validator) {
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

    public String addCreditCard(CreditCard creditCard){
        String errors = "Error\n";
        boolean existsWithNumber = !creditCardDAO.showCreditCardByNumber(creditCard.getNumber()).isEmpty();
        boolean notExistsUser = userDAO.showUser(creditCard.getUser_id()).isEmpty();
        if(existsWithNumber){
            errors += "Credit card with such an number already exists\n";
        }
        if(notExistsUser){
            errors += "No user with such an id\n";
        }
        BindingResult bindingResult = new BeanPropertyBindingResult(creditCard, "credit card");
        validator.validate(creditCard, bindingResult);

        if(bindingResult.hasErrors() || existsWithNumber || notExistsUser){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            creditCardDAO.addCreditCard(creditCard);
            return "Added";
        }
    }

    public String updateCreditCard(int id, CreditCard creditCard) {
        String errors = "Error\n";
        boolean existsWithNumber = !creditCardDAO.showCreditCardByNumber(creditCard.getNumber()).isEmpty();
        boolean notExistsUser = userDAO.showUser(creditCard.getUser_id()).isEmpty();
        boolean notExistsCreditCard = creditCardDAO.showCreditCardById(id).isEmpty();
        if(existsWithNumber)
            errors += "Credit card with such an number already exists\n";
        if(notExistsUser)
            errors += "No user with such an id\n";
        if(notExistsCreditCard)
            errors += "No credit card with such an id\n";
        BindingResult bindingResult = new BeanPropertyBindingResult(creditCard, "credit card");
        validator.validate(creditCard, bindingResult);

        if(bindingResult.hasErrors() || existsWithNumber || notExistsUser || notExistsCreditCard){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            creditCardDAO.updateCreditCard(id, creditCard);
            return "Updated";
        }
    }

    public String deleteCreditCard(int id){
        String errors = "Error\n";
        if(creditCardDAO.showCreditCardById(id).isEmpty()){
            errors += "No credit card with such id\n";
            return errors;
        } else {
            creditCardDAO.deleteCreditCard(id);
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
