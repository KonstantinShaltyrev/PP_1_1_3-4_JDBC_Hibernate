package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.logging.Level;

public class Main {
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 32);
        userService.saveUser("Jamie", "Oliver", (byte) 23);
        userService.saveUser("James", "Newman", (byte) 11);
        userService.saveUser("Gordon", "Ramsay", (byte) 48);

        System.out.println("\n All users list");
        userService.getAllUsers().forEach(p -> System.out.println(p.toString()));

        System.out.println("\n Id 2 was removed");
        userService.removeUserById(2);
        userService.getAllUsers().forEach(p -> System.out.println(p.toString()));

        System.out.println("\n Table cleaning");
        userService.cleanUsersTable();
        userService.getAllUsers().forEach(p -> System.out.println(p.toString()));

        System.out.println("\n Table drop");
        userService.dropUsersTable();
    }
}
