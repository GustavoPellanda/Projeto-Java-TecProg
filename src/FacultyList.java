import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This class creates a list of faculty members.
 */

public class FacultyList {
    private List<String> facultyList; // Will store the names of the faculty
    private Map<String, Integer> membershipTimeMap; // Will store the time of membership of each faculty

    public FacultyList() {
        facultyList = new ArrayList<>();
        membershipTimeMap = new HashMap<>();
    }

    public void addFaculty(String name) {
        if (!isDuplicate(name)) {
            String formattedName = formatName(name);
            facultyList.add(formattedName);
            membershipTimeMap.put(formattedName, 0); // Initializes membership time as 0
        }
    }

    public List<String> getFacultyList() {
        return facultyList;
    }

    public boolean removeFaculty(String name) {
        Optional<String> facultyToRemove = facultyList.stream()
                .filter(faculty -> faculty.equalsIgnoreCase(name))
                .findFirst();
        if (facultyToRemove.isPresent()) {
            String formattedName = facultyToRemove.get();
            facultyList.remove(formattedName);
            membershipTimeMap.remove(formattedName);
            return true;
        }
        return false;
    }

    // Methods used to manage the membership time of each faculty member:

    public void incrementMembershipTime() {
        facultyList.forEach(name -> {
            int currentTime = membershipTimeMap.getOrDefault(name, 0);
            membershipTimeMap.put(name, currentTime + 1);
        });
    }

    public void setMembershipTime(String name, int time) {
        String formattedName = formatName(name);
        if (facultyList.contains(formattedName)) {
            membershipTimeMap.put(formattedName, time);
        }
    }    

    public Map<String, Integer> getMembershipTime() {
        return membershipTimeMap;
    }

    // Methods used to insert a new faculty member:

    //Checks if the name is already on the list
    private boolean isDuplicate(String name) {
        return facultyList.stream().anyMatch(faculty -> faculty.equalsIgnoreCase(name));
    }

    // Capitalizes the first letter of each word in the name
    private String formatName(String name) {
        String[] nameParts = name.split(" ");
        StringBuilder formattedName = new StringBuilder();

        for (String part : nameParts) {
            if (!part.isEmpty()) {
                formattedName.append(part.substring(0, 1).toUpperCase())  // Capitalizes first letter
                             .append(part.substring(1).toLowerCase())   // Makes the rest of the letters lowercase
                             .append(" ");  // Adds a space between the names
            }
        }

        // Trims the trailing space and return the formatted name
        return formattedName.toString().trim();
    }
}