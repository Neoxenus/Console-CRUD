package com.console.crud.services.implementations;

import com.console.crud.DAO.UserDAO;
import com.console.crud.DTO.UserDTO;
import com.console.crud.entities.User;
import com.console.crud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final SmartValidator validator;
    @Autowired
    public UserServiceImpl(UserDAO userDAO, SmartValidator validator) {
        this.userDAO = userDAO;
        this.validator = validator;
    }

    @Override
    public String showAll(){
        List<User> users = userDAO.showAll();
        if (!users.isEmpty())
            return users.stream().map(User::toString).collect(Collectors.joining("\n"));
        else
            return "Nothing to display";
    }

    @Override
    public String addUser(UserDTO userDTO){


        String errors = "Error\n";
        boolean existsWithEmail = userDAO.showUserByEmail(userDTO.getEmail()).isPresent();
        if(existsWithEmail){
            errors += "User with such an email already exists\n";
        }

        User user = new User(userDTO);

        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, bindingResult);

        if(bindingResult.hasErrors() || existsWithEmail){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            try {
                userDAO.addUser(user);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }

            return "Added";
        }
    }

    @Override
    public String updateUser(int id, UserDTO userDTO) {




        String errors = "Error\n";
        boolean notExistsWithId = userDAO.showUser(id).isEmpty();
        boolean existsWithEmail = userDAO.showUserByEmail(userDTO.getEmail())
                .map(user -> !user.getId().equals(id))
                .orElse(false);

        if(notExistsWithId){
            errors += "No users with such id\n";
        }
        if(existsWithEmail){
            errors += "User with such an email already exists\n";
        }

        User user = new User(userDTO);
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, bindingResult);

        if(bindingResult.hasErrors() || existsWithEmail || notExistsWithId){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            try {
                userDAO.updateUser(id, user);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }

            return "Updated";
        }
    }

    @Override
    public String deleteUser(int id){
        String errors = "Error\n";
        if(userDAO.showUser(id).isEmpty()){
            errors += "No users with such id\n";
            return errors;
        } else {
            try {
                userDAO.deleteUser(id);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }

            return "Deleted";
        }
    }

}
