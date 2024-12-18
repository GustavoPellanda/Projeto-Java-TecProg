import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FacultyList facultyList = new FacultyList();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter faculty member name (or 'exit' to quit): ");
            String name = scanner.nextLine();

            if (name.equalsIgnoreCase("exit")) {
                break;
            }

            facultyList.addFaculty(name);

            List<String> list = facultyList.getFacultyList();
            System.out.println("Current Faculty Members:");
            for (String facultyName : list) {
                System.out.println(facultyName);
            }
            System.out.println();
        }

        scanner.close();
        System.out.println("Exiting...");
    }
}
