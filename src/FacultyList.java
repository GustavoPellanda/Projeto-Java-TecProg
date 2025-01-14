import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class creates a list of faculty members.
 */

public class FacultyList {
    private List<String> facultyList;

    public FacultyList() {
        facultyList = new ArrayList<>();
    }

    public void addFaculty(String name) {
        if (!isDuplicate(name)) {
            facultyList.add(formatName(name));
        }
    }

    public List<String> getFacultyList() {
        return facultyList;
    }

    public boolean removeFaculty(String name) {
        Optional<String> facultyToRemove = facultyList.stream()
        .filter(faculty -> faculty.equalsIgnoreCase(name))
        .findFirst();
        facultyToRemove.ifPresent(facultyList::remove);
        
        return facultyToRemove.isPresent();
    }

    // Methods used to insert a new faculty member:

    // Checks if the name is already on the list:
    private boolean isDuplicate(String name) {
        return facultyList.stream().anyMatch(faculty -> faculty.equalsIgnoreCase(name));
    }

    // Capitalizes the first letter of each word in the name
    private String formatName(String name) {
        String[] nameParts = name.split(" ");
        StringBuilder formattedName = new StringBuilder();
        
        for (String part : nameParts) {
            if (!part.isEmpty()) {
                formattedName.append(part.substring(0, 1).toUpperCase())  // Capitalize first letter
                              .append(part.substring(1).toLowerCase())   // Make the rest of the letters lowercase
                              .append(" ");  // Add a space between the names
            }
        }
        
        // Trim the trailing space and return the formatted name
        return formattedName.toString().trim();
    }
}