package ua.kiev.prog;

import java.util.Scanner;

public class Interview {
    static Scanner sc = new Scanner(System.in);

    public static void selected() {
        while (true) {
            System.out.println("1: add client to base");
            System.out.println("2: add product to base");
            System.out.println("3: add invoice to base");
            System.out.println("4: view client to base");
            System.out.println("5: view product to base");
            System.out.println("6: view invoice to base");
            System.out.print("-> ");
            String s = sc.nextLine();
            switch (s) {
                case "1":
                    Users users = new Users();
                    users.addToBase();
                    break;
                case "2":
                    Product product = new Product();
                    product.addToBase();
                    break;
                case "3":
                    Invoice invoice = new Invoice();
                    invoice.addToBase();
                    break;
                case "4":
                    users = new Users();
                    users.viewInfo();
                    break;
                case "5":
                    product = new Product();
                    product.viewInfo();
                    break;
                case "6":
                    invoice = new Invoice();
                    invoice.viewInfo();
                    break;
                default:
                    return;
            }
        }
    }
}
