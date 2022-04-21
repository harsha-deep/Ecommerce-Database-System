import java.util.Scanner;

public class Run {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Database db = new Database();
        db.init();

        db.listItems();

        db.close();

        if (scanner != null) {
            scanner.close();
        }

    }
}
