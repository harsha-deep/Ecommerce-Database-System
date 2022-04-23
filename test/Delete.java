import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Delete {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWD);

            System.out.println("Creating statement...");

            System.out.print("Enter dlocation: ");
            String name = scanner.next();
            String query = "DELETE FROM dept_locations WHERE dlocation = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
            scanner.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Sucessfully Ended.");
    }

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/testcompanydb?useSSL=false";
    private static final String USER = "harsha";
    private static final String PASSWD = "64316431";
}
