import java.util.Scanner;

public class Run {
    public static void instructions() {
        System.out.println("\n\n1. Add a customer");
        System.out.println("2. Remove a customer");
        System.out.println("3. Reset your password");
        System.out.println("4. List of Items");
        System.out.println("5. Compute your total bill");
        System.out.println("6. Add an Item");
        System.out.println("7. Place an order");
        System.out.println("0. Exit");
        System.out.print("\n\nEnter your choice: ");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Database db = new Database();
        db.init();

        System.out.println("\n\nHey! Welcome. Please select appropriate option: ");
        instructions();

        int choice = scanner.nextInt();

        while (choice != 0) {
            switch (choice) {
                case 1:
                    db.addCustomer();
                    break;

                case 2:
                    db.removeCustomer();
                    break;

                case 3:
                    db.resetPassword();
                    break;

                case 4:
                    db.listItems();
                    break;

                case 5:
                    db.totalizeBill();
                    break;

                case 6:
                    db.addItem();
                    break;

                case 7:
                    db.placeOrder();
                    break;

                case 0:
                    System.exit(0);

                default:
                    System.out.println("Invalid option");
                    break;

            }
            instructions();
            choice = scanner.nextInt();
        }

        db.close();

        if (scanner != null) {
            scanner.close();
        }

    }
}
