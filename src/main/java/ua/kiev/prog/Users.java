package ua.kiev.prog;

import java.sql.*;
import java.util.Scanner;

public class Users implements BaseOfOrders {
    static Scanner sc = new Scanner(System.in);
    private int id;
    private String name;
    private String surname;

    public Users() {
    }

    public Users(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    @Override
    public void addToBase() {
        System.out.print("Enter client name: ");
        String name = sc.nextLine();
        System.out.print("Enter client surname: ");
        String surname = sc.nextLine();
        String sql = "INSERT INTO users (first_name, surname) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = ConnectSql.connectDB().prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.executeUpdate();
            System.out.println("Client add to base.");
        } catch (SQLException e) {
            System.out.println("Adding failed.");
        }
    }

    @Override
    public void viewInfo() {
        String sql = "SELECT * FROM users";
        try {
            Statement statement = ConnectSql.connectDB().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Users users = new Users();
                users.setId(resultSet.getInt("user_id"));
                users.setName(resultSet.getString("first_name"));
                users.setSurname(resultSet.getString("surname"));
                System.out.println(users);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
 /*   CREATE TABLE IF NOT EXISTS users (user_id INT NOT NULL AUTO_INCREMENT " +
        "PRIMARY KEY, first_name VARCHAR(30) NOT NULL, surname VARCHAR(60) NOT NULL
*/