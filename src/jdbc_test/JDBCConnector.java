package jdbc_test;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class JDBCConnector {
    private static final String DRIVER_PATH = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/xe";
    private static final String USER_NAME = "c##diary";
    private static final String PASSWORD = "1234";

    private static Connection con;

    public static Connection getConnection(){

        try {
            Class.forName(DRIVER_PATH);
            System.out.println("JDBC Driver Method");
            con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println("Connection Done sucessfully");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver class not Found");
        } catch (SQLException e) {
            System.out.println("Connection Error");;
        }
        return con;
    }

    public static void main(String[] args){
        Connection con = getConnection();
    }
}