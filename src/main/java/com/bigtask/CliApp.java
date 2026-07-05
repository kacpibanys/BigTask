package com.bigtask;

import com.bigtask.Repositories.InmemoryUserRepository;
import com.bigtask.Repositories.UserRepository;
import com.bigtask.User.CompanyUser;
import com.bigtask.User.IndividualUser;
import com.bigtask.User.User;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CliApp {
    public static void main(String[] args) {
        UserRepository userRepository = new InmemoryUserRepository();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Reservation system is working now. Enter HELP to get help");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            /*tutaj rozmawialem z llem o logice i podejsciu zeby stringi nie robily psikusow
            zeby spacje nie robily problemow to bede uzywal tablic na przechowywanie posplitowanych stringow
            pytalem tez jakie podejscia do tych cli sie uzywa, zeby nie robic jakiegos takiego prymitywnego interfejsu typu
            wybierz 1, 2, 3 i zeby sie robilo, tylko zeby mialo rece i nogi. */

            String[] parts = input.split("\\s+");
            String command = parts[0].toUpperCase();


            if (command.equals("QUIT")) {
                System.out.println("OK: System closed.");
                break;
            }


            try {
                switch (command) {
                    case "ADD_USER":
                        handleAddUser(parts, userRepository);
                        System.out.println("OK: User added successfully.");
                        break;

                    case "LIST_USERS":
                        handleListUsers(userRepository);
                        System.out.println("OK: Users list displayed.");
                        break;

                    case "HELP":
                        System.out.println("Available commands: ADD_USER, LIST_USERS, QUIT");
                        System.out.println("OK: Help displayed");
                        break;

                    default:
                        throw new IllegalArgumentException("Unknown command: " + command);
                }

            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        scanner.close();

    }

    private static void handleAddUser(String[] parts, UserRepository userRepository) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Missing arguments. Format: ADD_USER INDIVIDUAL <email> <fullName>");
        }

        String type = parts[1].toUpperCase();
        String email = parts[2];

        if (type.equals("INDIVIDUAL")) {
            String fullName = String.join(" ", Arrays.copyOfRange(parts, 3, parts.length));
            User user = new IndividualUser(email, fullName);
            userRepository.addUser(user);

        } else if (type.equals("COMPANY")) {
            if (parts.length < 6) {
                throw new IllegalArgumentException("Missing arguments. Format: ADD_USER COMPANY <email> <displayName_without_spaces> <companyName> <nip>");
            }

            String displayName = parts[3].replace("_", " ");

            String nip = parts[parts.length - 1];

            String companyName = String.join(" ", Arrays.copyOfRange(parts, 4, parts.length - 1));

            User user = new CompanyUser(email, displayName, companyName, nip);
            userRepository.addUser(user);

        } else {
            throw new IllegalArgumentException("Unknown user type: " + type + ". Format: INDIVIDUAL or COMPANY.");
        }
    }

    private static void handleListUsers(UserRepository userRepository) {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new IllegalStateException("No users found.");
        }

        for (User user : users) {
            System.out.println("- " + user.toString());
        }
    }

}
