import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class GeneralRetrieval {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/testcompanydb?useSSL=false";
    private static final String USER = "harsha";
    private static final String PASSWD = "64316431";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Connection conn = null;
        Statement st = null;

        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWD);

            System.out.println("Creating statement...");

            st = conn.createStatement();

            String query = null;
            System.out.print("Enter the arg: ");
            String arg = scanner.next();
            query = "SELECT " + arg + " from employee";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String result = rs.getString(arg);
                System.out.println(result);
            }

            rs.close();
            st.close();
            conn.close();
            scanner.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(st != null)
                st.close();
            } catch(SQLException se2){
                se2.printStackTrace();
            } try {
                if(conn != null) 
                conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Sucessfully Ended.");
    }
}