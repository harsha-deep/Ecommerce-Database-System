import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Update {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWD);

            System.out.println("Creating statement...");

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery("SELECT * FROM employee");

            while (rs.next()) {
                float salary = rs.getFloat("salary");
                // rs.updateFloat("salary", (float)(1.1 * salary));
                rs.updateFloat("salary", Double.valueOf(salary / 1.1).floatValue());
                rs.updateRow();
            }

            rs.close();
            stmt.close();
            conn.close();
            scanner.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
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
