import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FacultyList facultyList = new FacultyList();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter faculty member name, 'remove <name>' to remove, or 'exit' to quit: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            if (input.toLowerCase().startsWith("remove ")) {
                String nameToRemove = input.substring(7).trim();
                boolean removed = facultyList.removeFaculty(nameToRemove);
                if (removed) {
                    System.out.println(nameToRemove + " was removed.\n");
                } else {
                    System.out.println(nameToRemove + " not found.");
                }
            } else {
                facultyList.addFaculty(input);
            }

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