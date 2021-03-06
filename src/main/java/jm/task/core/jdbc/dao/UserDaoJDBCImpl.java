package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = null;

        try {
            connection = getConnection();
            connection.createStatement().execute("CREATE TABLE if not exists users(\n" +
                    "id       bigint         NOT NULL PRIMARY KEY AUTO_INCREMENT,\n" +
                    "name     VARCHAR(50) NOT NULL,\n" +
                    "lastName VARCHAR(50) NOT NULL,\n" +
                    "age      TINYINT \n" +
                    ")");
            connection.commit();

        } catch (SQLException e) {
            System.err.println("Ошибка создания таблицы");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException SQLe) {
                SQLe.printStackTrace();
                System.err.println("Попытка отменить изменения, после попытки создания таблицы - неудачна");
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Ошибка закрытия соединения с БД во время создания таблицы");
                    e.printStackTrace();
                }
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = null;

        try {
            connection = getConnection();
            connection.createStatement().execute("drop table if exists users");
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException SQLe) {
                System.err.println("Попытка отменить изменения, при удалении таблицы - неудачна");
                SQLe.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Ошибка закрытия БД при удалении таблицы");
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into users(name, lastName, age) VALUES (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Ошибка при сохранении User в таблицу");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException SQLe) {
                System.err.println("Попытка отменить изменения, после попытки сохранить User в таблицу - неудача");
                SQLe.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Ошибка закрытия БД при сохранении User в таблицу");
                    e.printStackTrace();
                }
            }
        }

    }

    public void removeUserById(long id) {
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from users where id = ?");
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении User по Id");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException SQLe) {
                System.err.println("Попытка отменить изменения, при удалении User по I - неудача");
                SQLe.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Ошибка закрытия БД при удалении User по Id");
                    e.printStackTrace();
                }
            }
        }

    }

    public List<User> getAllUsers() {
        Connection connection = null;

        List<User> users = new ArrayList<>();

        try {
            connection = getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users");
            connection.commit();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех User из таблицы");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException SQLe) {
                System.err.println("Попытка отменить изменения, при получении всех User из таблицы - неудача");
                SQLe.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Ошибка закрытия БД при получении всех User из таблицы");
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        Connection connection = null;

        try {
            connection = getConnection();
            connection.createStatement().execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы User");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException SQLe) {
                System.err.println("Попытка отменить изменения, при очистке таблицы User - неудача");
                SQLe.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Ошибка закрытия БД при очистке таблицы User");
                    e.printStackTrace();
                }
            }
        }
    }
}
