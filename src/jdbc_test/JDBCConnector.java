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
        // 1. jdbc 드라이버를 메모리에 로딩

        try {
            Class.forName(DRIVER_PATH);
            System.out.println("JDBC Driver Method");
            // 2. Connection
            con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println("Connection Done sucessfully");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver class not Found");
        } catch (SQLException e) {
            System.out.println("Connection Error");;
        }
        return con;
    }

//    public static void resultSetTest(){
//        // 3. 준비된 SQL 문을 실행할 수 있는 문장 객체 생성
//        try {
//            String sql  = "SELECT id, name, publish_name, author from book b, publish p\n" +
//                    " where b.publish_id = p.publish_id";
//
//            PreparedStatement pstmt = con.prepareStatement(sql);
//            ResultSet rs = pstmt.executeQuery();
//
//            while(rs.next()){
//                System.out.print(rs.getString("name") + "||");
//                System.out.print(rs.getString("publish_name"));
//                System.out.println();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public static void main(String[] args){
        Connection con = getConnection();
//        resultSetTest();
    }
}