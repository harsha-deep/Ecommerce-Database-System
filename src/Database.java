import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Database {

    public void init() {
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWD);

            System.out.println("Creating statement...");

            stmt = conn.createStatement();
            updt = conn.createStatement();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean auth(Statement stmt, int ownerID, String ownerPass) {

        boolean isAuth = false;
        String auth = null;

        try {
            auth = "SELECT * from owner";
            ResultSet rs = stmt.executeQuery(auth);

            while (rs.next()) {
                int gotID = rs.getInt("owner_id");
                String gotPass = rs.getString("owner_password");

                if (gotID == ownerID && gotPass.equals(ownerPass)) {
                    isAuth = true;
                    break;
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return isAuth;
    }

    public void addCustomer() {

        System.out.print("Enter ID: ");
        int custID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter name: ");
        String custName = scanner.nextLine();

        System.out.print("Enter mobile number: ");
        String custMobNum = scanner.nextLine();

        System.out.print("Enter email: ");
        String custEmail = scanner.nextLine();

        System.out.print("Enter password: ");
        String custPass = scanner.nextLine();

        System.out.print("Please enter the owner's ID: ");
        int ownerID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Please enter the owner's password: ");
        String ownerPass = scanner.nextLine();

        try {
            String query = "INSERT INTO customer (cust_id, cust_name, mobile, cust_email, cust_password) VALUES (?, ?, ?, ?, ?)";
            boolean isAuth = auth(stmt, ownerID, ownerPass);
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, custID);
            pstmt.setString(2, custName);
            pstmt.setString(3, custMobNum);
            pstmt.setString(4, custEmail);
            pstmt.setString(5, custPass);
            if (isAuth) {
                System.out.println("Authentication Sucessful.");
                pstmt.executeUpdate();
                System.out.println("Database Updated Sucessfully .");
            } else {
                System.out.println("Authentication Failed.");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Database() {
    }

    public void removeCustomer() {
        System.out.print("Enter ID: ");
        int custID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Please enter the owner's ID: ");
        int ownerID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Please enter the owner's password: ");
        String ownerPass = scanner.nextLine();

        try {
            String query = "DELETE FROM customer where cust_id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, custID);
            boolean isAuth = auth(stmt, ownerID, ownerPass);
            if (isAuth) {
                System.out.println("Authentication Sucessful.");
                int retval = pstmt.executeUpdate();
                if (retval != 0)
                    System.out.println("Database Updated Sucessfully .");
                else
                    System.out.println("Something went wrong! (or) User doesn't exist!");
            } else {
                System.out.println("Authentication Failed.");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void resetPassword() {
        System.out.print("Enter ID: ");
        int custID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Please enter the new password: ");
        String setPass = scanner.nextLine();

        System.out.print("Please enter the owner's ID: ");
        int ownerID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Please enter the owner's password: ");
        String ownerPass = scanner.nextLine();

        try {
            String query = "UPDATE customer SET cust_password = ? where cust_id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, setPass);
            pstmt.setInt(2, custID);

            boolean isAuth = auth(stmt, ownerID, ownerPass);

            if (isAuth) {
                System.out.println("Authentication Sucessful.");
                pstmt.executeUpdate();
                System.out.println("Database Updated Sucessfully .");
            } else {
                System.out.println("Authentication Failed.");
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {

        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }

        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se2) {
            se2.printStackTrace();
        }

        try {
            if (updt != null)
                updt.close();
        } catch (SQLException se3) {
            se3.printStackTrace();
        }

        try {
            if (pstmt != null)
                pstmt.close();
        } catch (SQLException se4) {
            se4.printStackTrace();
        }

        try {
            if (scanner != null)
                scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // database params
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/ecommerce_db?useSSL=false";

    // credentials
    private static final String USER = "harsha";
    private static final String PASSWD = "64316431";

    // Scanner
    private static Scanner scanner = new Scanner(System.in);

    private Connection conn = null;
    private Statement stmt = null;
    private Statement updt = null;
    private PreparedStatement pstmt = null;
}
