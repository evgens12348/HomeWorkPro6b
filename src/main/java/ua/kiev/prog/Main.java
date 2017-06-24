package ua.kiev.prog;

public class Main {
    public static void main(String[] args) {
        ConnectSql.createDB(ConnectSql.connectDB());
        Interview.selected();
    }
}
