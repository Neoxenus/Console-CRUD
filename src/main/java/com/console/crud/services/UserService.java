package com.console.crud.services;

import com.console.crud.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    String showAll();

    String addUser(User user);

    String updateUser(int id, User updatedUser);

    String deleteUser(int id);

}
