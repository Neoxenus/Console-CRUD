package com.console.crud.DAO;

import com.console.crud.entities.CreditCard;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardDAO {
    List<CreditCard> showCreditCardByNumber(String email);
    List<CreditCard> showCreditCardById(int id);
    List<CreditCard> showAll();

    void addCreditCard(CreditCard creditCard);

    void updateCreditCard(int id, CreditCard creditCard);

    void deleteCreditCard(int id);

    List<CreditCard> findByUserId(int userId);
}
