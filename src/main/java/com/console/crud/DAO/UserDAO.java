package com.console.crud.DAO;

import com.console.crud.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO {


    List<User> showAll();

    Optional<User> showUser(int id);
    Optional<User> showUserByEmail(String email);
    void addUser(User user);


    void updateUser(int id, User updatedUser);

    void deleteUser(int id);
}
