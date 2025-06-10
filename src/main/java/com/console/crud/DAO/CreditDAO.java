package com.console.crud.DAO;

import com.console.crud.entities.Credit;

import java.util.List;

public interface CreditDAO {
    List<Credit> showAll();

    List<Credit> showCreditById(int id);
    List<Credit> showCreditsByCreditCardId(int creditCardId);
    List<Credit> showCreditsByUserId(int userId);
    void addCredit(Credit credit);

    void updateCredit(int id, Credit updatedCredit);

    void deleteCredit(int id);
}
