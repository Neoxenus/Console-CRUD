package com.console.crud.DAO;

import com.console.crud.entities.CreditCard;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditCardDAO {
    Optional<CreditCard> showCreditCardByNumber(String email);
    Optional<CreditCard> showCreditCardById(int id);
    List<CreditCard> showAll();

    void addCreditCard(CreditCard creditCard);

    void updateCreditCard(int id, CreditCard creditCard);

    void deleteCreditCard(int id);

    List<CreditCard> findByUserId(int userId);
}
