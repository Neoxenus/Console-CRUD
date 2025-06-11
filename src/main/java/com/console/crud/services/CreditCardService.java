package com.console.crud.services;

import com.console.crud.DTO.CreditCardDTO;
import org.springframework.stereotype.Service;

@Service
public interface CreditCardService {


    String showAll();

    String addCreditCard(CreditCardDTO creditCard);

    String updateCreditCard(int id, CreditCardDTO creditCard);

    String deleteCreditCard(int id);

    String findByUserId(int userId);
}
