import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class is responsible for choosing who the next coordinator will be.
 */

 public class CoordinatorPicker {

    private String coordinator;
    private String viceCoordinator;

    private static final String COORDINATORS_FILE = "coordinators.txt";

    public CoordinatorPicker() {
        this.coordinator = "";
        this.viceCoordinator = "";
    }

    // Selects the next coordinator and vice-coordinator
    public void chooseCoordinator(List<String> facultyList, Map<String, Integer> membershipTimeMap) {
        if (facultyList == null || facultyList.isEmpty()) {
            return;
        }
    
        // In the first election, there is no vice-coordinator
        if (viceCoordinator == null || viceCoordinator.isEmpty()) {
            viceCoordinator = pickFacultyMember(facultyList, membershipTimeMap);
            coordinator = pickFacultyMember(facultyList, membershipTimeMap);
        }
    
        coordinator = viceCoordinator;
        viceCoordinator = pickFacultyMember(facultyList, membershipTimeMap);
    
        saveCoordinatorOnList(coordinator);
    }

    // Randomly selects a faculty member from the list 
    public String pickFacultyMember(List<String> facultyList, Map<String, Integer> membershipTimeMap) {
        Random random = new Random();
        String selected;
        do {
            selected = facultyList.get(random.nextInt(facultyList.size()));
        } while (selected.equals(coordinator) || selected.equals(viceCoordinator) || // Ensures the selected member is not the current coordinator or vice-coordinator
                membershipTimeMap.getOrDefault(selected, 0) < 3 || // Ensures the membership time is more than 3
                isCoordinatorOnList(selected)); // Ensures the selected member has mever been a coordinator before

        return selected;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public String getViceCoordinator() {
        return viceCoordinator;
    }

    // Methods for keeping track of who has already been a coordinator:

    public void saveCoordinatorOnList(String coordinator) {
        if (!isCoordinatorOnList(coordinator)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(COORDINATORS_FILE, true))) {
                writer.write(coordinator);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isCoordinatorOnList(String name) {
        try (BufferedReader reader = new BufferedReader(new FileReader(COORDINATORS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase(name)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

