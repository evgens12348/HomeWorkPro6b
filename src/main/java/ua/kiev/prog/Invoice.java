package ua.kiev.prog;

import java.sql.*;
import java.util.Scanner;

public class Invoice implements BaseOfOrders {
    static Scanner sc = new Scanner(System.in);
    private int id;
    private int userId;
    private int productId;
    private int quantity;
    private double sum;

    public Invoice() {
    }

    public Invoice(int id, int userId, int productId, int quantity, double sum) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    private double calculationSum(int id, int quantity) {
        String sql = "SELECT price FROM product WHERE product_id=?";
        double price = 0;
        try {
            PreparedStatement preparedStatement = ConnectSql.connectDB().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                price = resultSet.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price * quantity;
    }

    private boolean checkQuantity(int id, int quantity) {
        String sql = "SELECT * FROM product WHERE product_id=?";
        boolean check = false;
        try {
            PreparedStatement preparedStatement = ConnectSql.connectDB().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            int tmp = 0;
            while (resultSet.next()) {
                tmp = resultSet.getInt("quantity");
            }
            if (quantity <= tmp) check = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    private void changeInQuantity(int id, int quan) {
        String sql = "UPDATE product SET quantity = quantity-? WHERE product_id=?";
        try {
            PreparedStatement preparedStatement = ConnectSql.connectDB().prepareStatement(sql);
            preparedStatement.setInt(1, (quan));
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", sum=" + sum +
                '}';
    }

    @Override
    public void addToBase() {
        System.out.println("Want to see a list of customers? (1-yes, 2-no)");
        String t = sc.nextLine();
        if (t.equals("1")) {
            Users users = new Users();
            users.viewInfo();
        }
        System.out.print("Enter client ID: ");
        String sClientID = sc.nextLine();
        Integer clientID = Integer.parseInt(sClientID);
        System.out.println("Want to see a list of products? (1-yes, 2-no)");
        t = sc.nextLine();
        if (t.equals("1")) {
            Product product = new Product();
            product.viewInfo();
        }
        System.out.print("Enter product ID: ");
        String sProductID = sc.nextLine();
        Integer productID = Integer.parseInt(sProductID);
        System.out.print("Enter quantity product: ");
        String sQuantity = sc.nextLine();
        Integer quantity = Integer.parseInt(sQuantity);
        Double sum = calculationSum(productID, quantity);
        String sql = "INSERT INTO invoice (user_id, product_id, quantity, sum) VALUES (?, ?, ?, ?)";
        try {
            if (checkQuantity(productID, quantity)) {
                changeInQuantity(productID,quantity);
                PreparedStatement preparedStatement = ConnectSql.connectDB().prepareStatement(sql);
                preparedStatement.setInt(1, clientID);
                preparedStatement.setInt(2, productID);
                preparedStatement.setInt(3, quantity);
                preparedStatement.setDouble(4, sum);
                preparedStatement.executeUpdate();
                System.out.println("Invoice add to base.");
            } else {
                System.out.println("Insufficient product");
            }
        } catch (SQLException e) {
            System.out.println("Adding failed.");
        }

    }


    @Override
    public void viewInfo() {
        String sql = "SELECT * FROM invoice";
        try {
            Statement statement = ConnectSql.connectDB().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(resultSet.getInt("invoice_id"));
                invoice.setUserId(resultSet.getInt("user_id"));
                invoice.setProductId(resultSet.getInt("product_id"));
                invoice.setQuantity(resultSet.getInt("quantity"));
                invoice.setSum(resultSet.getDouble("sum"));
                System.out.println(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
/*CREATE TABLE IF NOT EXISTS invoice (invoice_id INT NOT NULL AUTO_INCREMENT " +
                    "PRIMARY KEY, user_id INT NOT NULL, product_id INT NOT NULL, quantity INT NOT NULL, sum DOUBLE NOT NULL, " +
                    "FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT, " +
                    "FOREIGN KEY (product_id) REFERENCES product(product_id) ON UPDATE CASCADE ON DELETE RESTRICT)*/