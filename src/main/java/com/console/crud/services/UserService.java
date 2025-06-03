package com.console.crud.services;

import com.console.crud.DAO.UserDAO;
import com.console.crud.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
//@Transactional
public class UserService {

    private final UserDAO userDAO;
    private final SmartValidator validator;
    @Autowired
    public UserService(UserDAO userDAO, SmartValidator validator) {
        this.userDAO = userDAO;
        this.validator = validator;
    }

//    @Transactional(readOnly = true)
    public String showAll(){
        List<User> users = userDAO.showAll();
        if (!users.isEmpty())
            return users.stream().map(User::toString).collect(Collectors.joining("\n"));
        else
            return "Nothing to display";
    }

    public String addUser(User user){
        String errors = "Error\n";
        boolean existsWithEmail = !userDAO.showUserByEmail(user.getEmail()).isEmpty();
        if(existsWithEmail){
            errors += "User with such an email already exists\n";
        }

        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, bindingResult);

        if(bindingResult.hasErrors() || existsWithEmail){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            userDAO.addUser(user);
            return "Added";
        }

    }


    public String updateUser(int id, User updatedUser) {

        BindingResult bindingResult = new BeanPropertyBindingResult(updatedUser, "user");
        validator.validate(updatedUser, bindingResult);
        String errors = "Error\n";
        boolean notExistsWithId = userDAO.showUser(id).isEmpty();
        boolean existsWithEmail = !userDAO.showUserByEmail(updatedUser.getEmail()).isEmpty();
        if(notExistsWithId){
            errors += "No users with such id\n";
        }
        if(existsWithEmail){
            errors += "User with such an email already exists\n";
        }
        if(bindingResult.hasErrors() || existsWithEmail || notExistsWithId){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            userDAO.updateUser(id, updatedUser);
            return "Updated";
        }
    }

    public String deleteUser(int id){
        String errors = "Error\n";
        if(userDAO.showUser(id).isEmpty()){
            errors += "No users with such id\n";
            return errors;
        } else {
            userDAO.deleteUser(id);
            return "Deleted";
        }
    }

}
