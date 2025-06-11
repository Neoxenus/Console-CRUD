package com.console.crud.DAO.implementations.JDBC;

import com.console.crud.DAO.CreditCardDAO;
import com.console.crud.entities.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CreditCardDaoJdbcImpl implements CreditCardDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CreditCardDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<CreditCard> showCreditCardByNumber(String email){
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM credit_cards WHERE number=?",
                new BeanPropertyRowMapper<>(CreditCard.class),
                email));

    }
    public Optional<CreditCard> showCreditCardById(int id){
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM credit_cards WHERE id=?",
                new BeanPropertyRowMapper<>(CreditCard.class),
                id));

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
                creditCard.getBalance(), creditCard.getUser().getId());
    }

    public void updateCreditCard(int id, CreditCard creditCard) {
        jdbcTemplate.update(
                "UPDATE credit_cards SET number=?,cvv=?, expire_date=?, balance=?, user_id=? WHERE id=?",
                creditCard.getNumber(), creditCard.getCvv(), creditCard.getExpireDate(),
                creditCard.getBalance(),creditCard.getUser().getId(), id);
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
