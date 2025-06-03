package com.console.crud.DAO;

import com.console.crud.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO {


    List<User> showAll();

    List<User> showUser(int id);
    List<User> showUserByEmail(String email);
    void addUser(User user);


    void updateUser(int id, User updatedUser);

    void deleteUser(int id);
}
