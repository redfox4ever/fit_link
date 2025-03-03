package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Singleton Design Pattern
public class MyConnection {

    private final String URL = "jdbc:mysql://localhost:3306/fitlink";
    private final String USER = "root";
    private final String PASS = "";
    private Connection connection;
    private static MyConnection instance;

    public MyConnection(){
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connection established");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static MyConnection getInstance(){
        if(instance == null)
            instance = new MyConnection();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
