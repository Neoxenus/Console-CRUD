package com.console.crud.services;

import com.console.crud.DTO.CreditDTO;
import org.springframework.stereotype.Service;

@Service
public interface CreditService {

    String showAll();

    String showAllByUserId(int userId);

    String addCredit(CreditDTO creditDTO);
    String updateCredit(int id, CreditDTO updatedCredit);

    String deleteCredit(int id);

}
