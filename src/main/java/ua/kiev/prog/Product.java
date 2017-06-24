package ua.kiev.prog;

import java.sql.*;
import java.util.Scanner;


public class Product implements BaseOfOrders {
    static Scanner sc = new Scanner(System.in);
    private int id;
    private String name;
    private double price;
    private int quantity;

    public Product() {
    }

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public void addToBase() {
        System.out.print("Enter product name: ");
        String name = sc.nextLine();
        System.out.print("Enter product price: ");
        String sPrice = sc.nextLine();
        Double price = Double.parseDouble(sPrice);
        System.out.print("Enter quantity product: ");
        String sQuantity = sc.nextLine();
        Integer quantity = Integer.parseInt(sQuantity);
        String sql = "INSERT INTO product (prod_name, price, quantity) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = ConnectSql.connectDB().prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, quantity);
            preparedStatement.executeUpdate();
            System.out.println("Product add to base.");
        } catch (SQLException e) {
            System.out.println("Adding failed.");
        }

    }


    @Override
    public void viewInfo() {
        String sql = "SELECT * FROM product";
        try {
            Statement statement = ConnectSql.connectDB().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("prod_name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQuantity(resultSet.getInt("quantity"));
                System.out.println(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

/* CREATE TABLE IF NOT EXISTS product (product_id INT NOT NULL AUTO_INCREMENT " +
                    "PRIMARY KEY, prod_name VARCHAR(60) NOT NULL, price DOUBLE NOT NULL, quantity INT NOT NULL)*/
