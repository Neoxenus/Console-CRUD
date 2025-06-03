package com.console.crud.DAO.implementations;


import com.console.crud.DAO.UserDAO;
import com.console.crud.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoJdbcImpl implements UserDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> showAll(){
        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }

    public List<User> showUser(int id){
        return jdbcTemplate.query("SELECT * FROM users WHERE id=?",
                new BeanPropertyRowMapper<>(User.class),
                id);

    }
    public List<User> showUserByEmail(String email){
        return jdbcTemplate.query("SELECT * FROM users WHERE email=?",
                new BeanPropertyRowMapper<>(User.class),
                email);

    }
    public void addUser(User user){
//        jdbcTemplate.update("INSERT INTO users VALUES(?, ?, ?)", user.getName(),user.getSurname(), user.getAge(),
//                user.getEmail());

        jdbcTemplate.update(
                "INSERT INTO users (name,surname,age, email) " +
                "VALUES(?, ?, ?, ?)",user.getName(),user.getSurname(), user.getAge(),
                user.getEmail() );
    }


    public void updateUser(int id, User updatedUser) {
        jdbcTemplate.update("UPDATE users SET name=?,surname=?, age=?, email=? WHERE id=?", updatedUser.getName(),
                updatedUser.getSurname(), updatedUser.getAge(), updatedUser.getEmail(), id);
    }

    public void deleteUser(int id){
        jdbcTemplate.update("DELETE FROM users WHERE id=?", id);
    }
}
