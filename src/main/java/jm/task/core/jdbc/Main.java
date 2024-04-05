package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Stepan", "Gusev", (byte) 30);
        userService.saveUser("Igor", "Vasiliev", (byte) 21);
        userService.saveUser("Alena", "Kornilova", (byte) 27);
        userService.saveUser("Kirill", "Antonov", (byte) 18);

        List<User> users = userService.getAllUsers();
        System.out.println(users);

        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
