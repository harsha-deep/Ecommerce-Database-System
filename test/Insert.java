import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Insert {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWD);

            System.out.println("Creating statement...");

            stmt = conn.createStatement();
            conn.setAutoCommit(false);

            stmt.addBatch("INSERT INTO dept_locations " + "VALUES(1, 'Portland')");
            // stmt.addBatch("INSERT INTO dept_locations " + "VALUES(1, 'Tod')");
            // stmt.addBatch("INSERT INTO dept_locations " + "VALUES(1, 'Oregon')");

            // int[] updateCounts = stmt.executeBatch();
            stmt.executeBatch();

            // for (int i = 0; i < updateCounts.length; i++)
            // System.out.println(updateCounts[i]);

            conn.commit();

            stmt.close();
            // conn.close();
            scanner.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
