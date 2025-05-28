package com.console.crud;

import com.console.crud.entities.CreditCard;
import com.console.crud.entities.User;
import com.console.crud.services.CreditCardService;
import com.console.crud.services.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
public class Controller {

//    private UserDAO userDAO;
    private final UserService userService;
    private final CreditCardService creditCardService;

    public Controller(CreditCardService creditCardService, UserService userService) {
        this.creditCardService = creditCardService;
        this.userService = userService;
    }


    public void mainLoop(){
        boolean isExit = false;

        System.out.println("Type 1 to show all users");
        System.out.println("Type 2 to save new user");
        System.out.println("Type 3 to update user");
        System.out.println("Type 4 to delete user");

        System.out.println("Type 5 to show all credit cards of user");

        System.out.println("Type 6 to show all credit cards");
        System.out.println("Type 7 to save new credit card");
        System.out.println("Type 8 to update credit card");
        System.out.println("Type 9 to delete credit card");
        System.out.println("Type 0 to exit");

        Scanner scanner = new Scanner(System.in);

        while(!isExit){
            System.out.println("Type number of command: ");

            Integer command;
            try{
                command = Integer.valueOf(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Incorrect command");
                continue;
            }



            try{
                switch (command) {
                    case 1 -> {
                        System.out.println(userService.showAll());
                    }
                    case 2 -> {//create user
                        System.out.println("Enter user name: ");
                        String name = scanner.nextLine();

                        System.out.println("Enter user surname: ");
                        String surname = scanner.nextLine();

                        System.out.println("Enter user age: ");
                        int age;
                        try{
                            age = scanner.nextInt();
                        } catch (Exception e){
                            System.out.println("Error:\nAge must be number");
                            scanner.nextLine(); //fix of broken scanner
                            continue;
                        }

                        scanner.nextLine();
                        System.out.println("Enter user email: ");


                        String email = scanner.nextLine();
                        User user = new User(name, surname, age, email);

                        System.out.println(userService.addUser(user));

                    }
                    case 3 -> {//update user
                        System.out.println("Enter id of user to update");

                        int id;
                        try{
                            id = scanner.nextInt();
                        } catch (Exception e){
                            System.out.println("Error:\nId must be a number");
                            scanner.nextLine(); //fix of broken scanner
                            continue;
                        }
                        scanner.nextLine();//fix of broken scanner


                        System.out.println("Enter user name: ");
                        String name = scanner.nextLine();

                        System.out.println("Enter user surname: ");
                        String surname = scanner.nextLine();

                        System.out.println("Enter user age: ");
                        int age;
                        try{
                            age = scanner.nextInt();
                        } catch (Exception e){
                            System.out.println("Age must be a number");
                            continue;
                        }
                        scanner.nextLine();

                        System.out.println("Enter user email: ");
                        String email = scanner.nextLine();


                        System.out.println(userService.updateUser(id, new User(name, surname, age, email)));
                    }
                    case 4 -> {//delete user
                        System.out.println("Enter id of user that you want to delete");
                        int id;
                        try{
                            id = scanner.nextInt();
                        } catch (Exception e){
                            System.out.println("Error:\nId must be a number");
                            scanner.nextLine(); //fix of broken scanner
                            continue;
                        }
                        scanner.nextLine();

                        System.out.println(userService.deleteUser(id));
                    }
                    case 5 -> {//show credit cards of some user(by user id)
                        System.out.println("Entry user id");
                        int id;
                        try{
                            id = scanner.nextInt();
                        } catch (Exception e){
                            System.out.println("Error:\nId must be a number");
                            scanner.nextLine(); //fix of broken scanner
                            continue;
                        }

                        scanner.nextLine();
                        System.out.println(creditCardService.findByUserId(id));
                    }
                    case 6 -> { // show all credit cards
                        System.out.println(creditCardService.showAll());
                    }
                    case 7 -> {//adding credit card
                        System.out.println("Enter card number (16 numbers): ");
                        String number = scanner.nextLine();

                        System.out.println("Enter user cvv (3 numbers): ");
                        String cvv = scanner.nextLine();

                        System.out.println("Enter card expire date (format: mm-yyyy or m-yyyy): ");
                        String expireDate = scanner.nextLine();

                        System.out.println("Enter user id: ");
//                        scanner.nextInt();
                        int userId;
                        try{
                            userId = scanner.nextInt();
                        }catch (Exception e){
                            System.out.println("Error:\nId must be a number");
                            scanner.nextLine(); //fix of broken scanner
                            continue;
                        }

                        scanner.nextLine();
                        CreditCard creditCard = new CreditCard(number, cvv, expireDate, userId);

                        System.out.println(creditCardService.addCreditCard(creditCard));
                    }
                    case 8 -> {//update credit card
                        System.out.println("Enter id of card to update");
                        int id;
                        try{
                            id = scanner.nextInt();
                        }catch (Exception e){
                            System.out.println("Error:\nId must be a number");
                            scanner.nextLine(); //fix of broken scanner
                            continue;
                        }
                        scanner.nextLine();

                        System.out.println("Enter card number: ");
                        String number = scanner.nextLine();

                        System.out.println("Enter user cvv: ");
                        String cvv = scanner.nextLine();

                        System.out.println("Enter card expire date: ");
                        String expireDate = scanner.nextLine();

                        System.out.println("Enter user id: ");
                        int userId;
                        try{
                            userId = scanner.nextInt();
                        } catch (Exception e){
                            System.out.println("Error:\nId must be a number");
                            scanner.nextLine(); //fix of broken scanner
                            continue;
                        }
                        scanner.nextLine();

                        CreditCard creditCard = new CreditCard(number, cvv, expireDate, userId);

                        System.out.println(creditCardService.updateCreditCard(id, creditCard));
//                        System.out.println("Updated");
                    }
                    case 9 -> {//delete credit card (by id)
                        System.out.println("Entry id of credit card that you want to delete");
                        int id;
                        try{
                            id = scanner.nextInt();
                        }catch (Exception e){
                            System.out.println("Error:\nId must be a number");
                            scanner.nextLine(); //fix of broken scanner
                            continue;
                        }
                        scanner.nextLine();

                        System.out.println(creditCardService.deleteCreditCard(id));
//                        System.out.println("Deleted");
                    }
                    case 0 -> {//exit
                        isExit = true;
                        System.out.println("Exiting");
                        return;
                    }

                    default -> System.out.println("Incorrect command");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Incorrect input");
            }

        }
    }
}
