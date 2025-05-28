package com.console.crud.DAO;


import com.console.crud.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
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
    public int addUser(User user){
//        jdbcTemplate.update("INSERT INTO users VALUES(?, ?, ?)", user.getName(),user.getSurname(), user.getAge(),
//                user.getEmail());

        return jdbcTemplate.update(
                "INSERT INTO users (name,surname,age, email) " +
                "VALUES(?, ?, ?, ?)",user.getName(),user.getSurname(), user.getAge(),
                user.getEmail() );
    }


    public void updateUser(int id, User updatedUser) {
        jdbcTemplate.update("UPDATE users SET name=?,surname=?, age=?, email=? WHERE id=?", updatedUser.getName(),
                updatedUser.getSurname(), updatedUser.getAge(), updatedUser.getEmail(), id);
    }

    public int deleteUser(int id){
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id);
    }
}
