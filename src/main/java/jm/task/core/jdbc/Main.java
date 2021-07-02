package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanov", (byte) 20);
        System.out.println("User с именем – Ivan добавлен в базу данных");

        userService.saveUser("Kirill", "Kirillov", (byte) 18);
        System.out.println("User с именем – Kirill добавлен в базу данных");

        userService.saveUser("Aleksandr", "Aleksandrov", (byte) 35);
        System.out.println("User с именем – Aleksandr добавлен в базу данных");

        userService.saveUser("Igor", "Igorev", (byte) 15);
        System.out.println("User с именем – Igor добавлен в базу данных");

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();


    }
}
