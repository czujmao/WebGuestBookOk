package WebGuestBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnector {
    static Connection connection;
    public static void init() {
        if (null != connection) return;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/it_academy?user=root&password=");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }
    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}

