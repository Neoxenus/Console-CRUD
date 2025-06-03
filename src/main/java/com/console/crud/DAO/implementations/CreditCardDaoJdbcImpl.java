package com.console.crud.DAO.implementations;

import com.console.crud.DAO.CreditCardDAO;
import com.console.crud.entities.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CreditCardDaoJdbcImpl implements CreditCardDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CreditCardDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CreditCard> showCreditCardByNumber(String email){
        return jdbcTemplate.query("SELECT * FROM credit_cards WHERE number=?",
                new BeanPropertyRowMapper<>(CreditCard.class),
                email);

    }
    public List<CreditCard> showCreditCardById(int id){
        return jdbcTemplate.query("SELECT * FROM credit_cards WHERE id=?",
                new BeanPropertyRowMapper<>(CreditCard.class),
                id);

    }
    public List<CreditCard> showAll(){
        return jdbcTemplate.query(
                "SELECT * FROM credit_cards",
                new BeanPropertyRowMapper<>(CreditCard.class));
    }

    public void addCreditCard(CreditCard creditCard){
        jdbcTemplate.update(
                "INSERT INTO credit_cards (number, cvv, expire_date, balance, user_id) " +
                        "VALUES(?, ?, ?, ?, ?)",
                creditCard.getNumber(), creditCard.getCvv(), creditCard.getExpireDate(),
                creditCard.getBalance(), creditCard.getUser_id());
    }

    public void updateCreditCard(int id, CreditCard creditCard) {
        jdbcTemplate.update(
                "UPDATE credit_cards SET number=?,cvv=?, expire_date=?, balance=?, user_id=? WHERE id=?",
                creditCard.getNumber(), creditCard.getCvv(), creditCard.getExpireDate(),
                creditCard.getBalance(),creditCard.getUser_id(), id);
    }

    public void deleteCreditCard(int id){
        jdbcTemplate.update("DELETE FROM credit_cards WHERE id=?", id);
    }

    public List<CreditCard> findByUserId(int userId){
        return jdbcTemplate.query(
                "SELECT * FROM credit_cards WHERE user_id = ?",
                new BeanPropertyRowMapper<>(CreditCard.class),
                userId
        );
    }
}
