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

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean ownerAuth(Statement stmt, int ownerID, String ownerPass) {

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

    public boolean customerAuth(Statement stmt, int custID, String custPass) {
        boolean isAuth = false;
        String auth = null;

        try {
            auth = "SELECT * from customer";
            ResultSet rs = stmt.executeQuery(auth);

            while (rs.next()) {
                int gotID = rs.getInt("cust_id");
                String gotPass = rs.getString("cust_password");

                if (gotID == custID && gotPass.equals(custPass)) {
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

        System.out.print("Enter customer ID: ");
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
            boolean isAuth = ownerAuth(stmt, ownerID, ownerPass);
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

    public void removeCustomer() {
        System.out.print("Enter customer ID: ");
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
            boolean isAuth = ownerAuth(stmt, ownerID, ownerPass);
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
        System.out.print("Enter customer ID: ");
        int custID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter customer password: ");
        String custPass = scanner.nextLine();

        System.out.print("Please enter the new password: ");
        String setPass = scanner.nextLine();

        try {
            String query = "UPDATE customer SET cust_password = ? where cust_id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, setPass);
            pstmt.setInt(2, custID);

            boolean isAuth = customerAuth(stmt, custID, custPass);

            if (isAuth) {
                pstmt.executeUpdate();
                System.out.println("Database Updated Sucessfully.");
            } else {
                System.out.println("Authentication Failed.");
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listItems() {
        System.out.println("Please select appropriate option: ");
        System.out.println("1. List of all customers");
        System.out.println("2. List of all orders");
        System.out.println("3. List of all products");
        System.out.println("4. List of all transactions");
        System.out.print("\n\nEnter your choice: ");

        int option = scanner.nextInt();
        System.out.println("");

        System.out.print("Please enter the owner's ID: ");
        int ownerID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Please enter the owner's password: ");
        String ownerPass = scanner.nextLine();

        System.out.println("");

        boolean isAuth = ownerAuth(stmt, ownerID, ownerPass);
        if (isAuth) {
            try {
                String command = "SELECT * from %s";

                switch (option) {
                    case 1:
                        String query1 = String.format(command, "customer;");
                        ResultSet rs1 = stmt.executeQuery(query1);
                        while (rs1.next()) {
                            String custID = rs1.getString("cust_id");
                            String custName = rs1.getString("cust_name");
                            String custMobile = rs1.getString("mobile");
                            String custEmail = rs1.getString("cust_email");

                            System.out.println("Customer ID: " + custID);
                            System.out.println("Customer Name: " + custName);
                            System.out.println("Customer Mobile Number: " + custMobile);
                            System.out.println("Customer Email: " + custEmail);
                            System.out.println("");

                        }
                        try {
                            if (rs1 != null)
                                rs1.close();
                        } catch (SQLException se1) {
                            se1.printStackTrace();
                        }
                        break;

                    case 2:
                        String query2 = String.format(command, "online_order;");
                        ResultSet rs2 = stmt.executeQuery(query2);
                        while (rs2.next()) {
                            int orderID = rs2.getInt("order_id");
                            int custID = rs2.getInt("cust_id");
                            float price = rs2.getFloat("price");
                            String orderLocation = rs2.getString("order_location");
                            boolean dispatched = rs2.getBoolean("dispatched");

                            System.out.println("Order ID: " + Integer.toString(orderID));
                            System.out.println("Customer ID: " + Integer.toString(custID));
                            System.out.println("Price: " + Float.toString(price));
                            System.out.println("Location: " + orderLocation);
                            System.out.println("Dispatched: " + Boolean.toString(dispatched));
                            System.out.println("");

                        }
                        try {
                            if (rs2 != null)
                                rs2.close();
                        } catch (SQLException se2) {
                            se2.printStackTrace();
                        }
                        break;

                    case 3:
                        String query3 = String.format(command, "product");
                        ResultSet rs3 = stmt.executeQuery(query3);
                        while (rs3.next()) {
                            int productID = rs3.getInt("product_id");
                            int orderID = rs3.getInt("order_id");
                            String seller = rs3.getString("seller");
                            float price = rs3.getFloat("price");
                            String productName = rs3.getString("product_name");
                            String category = rs3.getString("category");

                            System.out.println("Product ID: " + Integer.toString(productID));
                            System.out.println("Order ID: " + Integer.toString(orderID));
                            System.out.println("Seller: " + seller);
                            System.out.println("Price: " + Float.toString(price));
                            System.out.println("Product Name: " + productName);
                            System.out.println("Category: " + category);
                            System.out.println("");
                        }
                        try {
                            if (rs3 != null)
                                rs3.close();
                        } catch (SQLException se3) {
                            se3.printStackTrace();
                        }
                        break;

                    case 4:
                        String query4 = String.format(command, "trans;");
                        ResultSet rs4 = stmt.executeQuery(query4);
                        while (rs4.next()) {
                            int transctionID = rs4.getInt("transaction_id");
                            int orderID = rs4.getInt("order_id");
                            int custID = rs4.getInt("cust_id");
                            String transctionStatus = rs4.getString("transaction_status");

                            System.out.println("Transaction ID: " + Integer.toString(transctionID));
                            System.out.println("Order ID: " + Integer.toString(orderID));
                            System.out.println("Customer ID: " + Integer.toString(custID));
                            System.out.println("Transaction Status: " + transctionStatus);
                            System.out.println("");
                        }

                        try {
                            if (rs4 != null)
                                rs4.close();
                        } catch (SQLException se4) {
                            se4.printStackTrace();
                        }
                        break;
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Authentication Failed.");
        }

    }

    public void totalizeBill() {

        System.out.print("Enter customer ID: ");
        int custID = scanner.nextInt();
        scanner.nextLine();

        double totalBill = 0;

        try {
            String query = "SELECT * FROM trans where cust_id = " + Integer.toString(custID);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                totalBill += rs.getInt("money_paid");
            }

            System.out.println("Total: " + Double.toString(totalBill));

            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException s) {
                s.printStackTrace();
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addItem() {
        System.out.print("Enter Product ID: ");
        int productID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the name of seller: ");
        String sellerName = scanner.nextLine();

        System.out.print("Enter the price: ");
        float price = scanner.nextFloat();
        scanner.nextLine();

        System.out.print("Enter the name of product: ");
        String ProductName = scanner.nextLine();

        System.out.print("Category of the product: ");
        String categoryName = scanner.nextLine();

        System.out.print("Please enter the owner's ID: ");
        int ownerID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Please enter the owner's password: ");
        String ownerPass = scanner.nextLine();

        try {
            String query = "INSERT INTO product (product_id, order_id, seller, price, product_name, category) VALUES (?, ?, ?, ?, ?, ?)";
            boolean isAuth = ownerAuth(stmt, ownerID, ownerPass);
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, productID);
            pstmt.setNull(2, java.sql.Types.INTEGER);
            pstmt.setString(3, sellerName);
            pstmt.setFloat(4, price);
            pstmt.setString(5, ProductName);
            pstmt.setString(6, categoryName);
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

    public void placeOrder() {
        System.out.print("Enter customer ID: ");
        int custID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the price: ");
        float price = scanner.nextFloat();
        scanner.nextLine();

        System.out.print("Enter the location: ");
        String orderLocation = scanner.nextLine();

        System.out.print("Enter password: ");
        String custPass = scanner.nextLine();

        try {
            String query = "INSERT INTO online_order (order_id, cust_id, price, order_location, dispatched) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, custID);
            pstmt.setInt(2, custID);
            pstmt.setFloat(3, price);
            pstmt.setString(4, orderLocation);
            pstmt.setBoolean(5, false);

            boolean isAuth = customerAuth(stmt, custID, custPass);
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
            if (pstmt != null)
                pstmt.close();
        } catch (SQLException se3) {
            se3.printStackTrace();
        }

        try {
            if (scanner != null)
                scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("See you again!");
    }

    // database params
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/ecommerce_db?useSSL=false";

    // credentials
    private static final String USER = "harsha";
    private static final String PASSWD = "64316431";

    // Scanner
    private static Scanner scanner = new Scanner(System.in);

    // java.sql objects
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
}
