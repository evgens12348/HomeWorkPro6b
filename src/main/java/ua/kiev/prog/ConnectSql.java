package ua.kiev.prog;

import java.sql.*;


public class ConnectSql {
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/db_orders";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Vfhbyf1975";
    private static final String DB_USERS="CREATE TABLE IF NOT EXISTS users (user_id INT NOT NULL AUTO_INCREMENT " +
            "PRIMARY KEY, first_name VARCHAR(30) NOT NULL, surname VARCHAR(60) NOT NULL)";
    private static final String DB_PRODUCT="CREATE TABLE IF NOT EXISTS product (product_id INT NOT NULL AUTO_INCREMENT " +
            "PRIMARY KEY, prod_name VARCHAR(60) NOT NULL, price DOUBLE NOT NULL, quantity INT NOT NULL)";
    private static final String DB_INVOICE="CREATE TABLE IF NOT EXISTS invoice (invoice_id INT NOT NULL AUTO_INCREMENT " +
            "PRIMARY KEY, user_id INT NOT NULL, product_id INT NOT NULL, quantity INT NOT NULL, sum DOUBLE NOT NULL, " +
            "FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT, " +
            "FOREIGN KEY (product_id) REFERENCES product(product_id) ON UPDATE CASCADE ON DELETE RESTRICT)";

    public static Connection connectDB() {
        Connection connection=null;
        try {
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    public static void createDB(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(DB_USERS);
            statement.execute(DB_PRODUCT);
            statement.execute(DB_INVOICE);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
