package uz.pdp.service;

import uz.pdp.model.Result;
import uz.pdp.model.User;

import java.sql.*;

public class DbService {
    String url = "jdbc:postgresql://localhost:5432/app-auth";
    String dbUser = "postgres";
    String dbPassword = "4999";

    public Result registerUser(User user) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Statement statement = connection.createStatement();
            String checkPhoneNumber = "select count(*) from users where phone_number = '"+ user.getPhoneNumber()+"'";
            ResultSet resultSet = statement.executeQuery(checkPhoneNumber);
            int countUserByFields = 0;
            while (resultSet.next()) {
                countUserByFields = resultSet.getInt(1);
            }
            if (countUserByFields > 0) {
                return new Result("Phone number already exists", false);
            }
            String checkUsernameQuery = "select count(*) from users where username = '"+ user.getUsername()+"'";
            ResultSet resultSetUsername = statement.executeQuery(checkUsernameQuery);
            while (resultSetUsername.next()) {
                countUserByFields = resultSetUsername.getInt(1);
            }
            if (countUserByFields > 0) {
                return new Result("Username already exists", false);
            }

            String query = "insert into users(username, first_name, last_name, phone_number, password) " +
                    "values('"+user.getUsername()+"','"+user.getFirstName()+"','"+user.getLastName()+"','"+user.getPhoneNumber()+"','"+user.getPassword()+"');";
            boolean execute = statement.execute(query);
            System.out.println(execute);
            return new Result("Successfully registered", true);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new Result("Error in server", false);
    }
    public User login(String username, String password) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String query = "select * from users where username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                username = resultSet.getString(2);
                String firstName = resultSet.getString(3);
                String lastName = resultSet.getString(4);
                String phoneNumber = resultSet.getString(5);
                return new User(id, firstName, lastName, phoneNumber, username);
            }
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public User loadUserByCookie(String username) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String query = "select * from users where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                username = resultSet.getString(2);
                String firstName = resultSet.getString(3);
                String lastName = resultSet.getString(4);
                String phoneNumber = resultSet.getString(5);
                return new User(id, firstName, lastName, phoneNumber, username);
            }
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
