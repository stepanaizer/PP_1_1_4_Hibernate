package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE if not exists `testdb`.`users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `firstName` VARCHAR(45) NULL,\n" +
                "  `lastName` VARCHAR(45) NULL,\n" +
                "  `age` INT NOT NULL,\n" +
                "  PRIMARY KEY (`id`));";

        try (Connection conn = Util.getConnection(); Statement statement = conn.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE if exists `testdb`.`users`";

        try (Connection conn = Util.getConnection(); Statement statement = conn.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (firstName, lastName, age) VALUES (?, ?, ?);";

        try (Connection conn = Util.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
        }
        System.out.println("User с именем " + name + " был добавлен в БД");
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id =" + id + ";";

        try (Connection conn = Util.getConnection(); Statement statement = conn.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
        }

    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Connection conn = Util.getConnection(); Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";

        try (Connection conn = Util.getConnection(); Statement statement = conn.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
        }
    }
}
