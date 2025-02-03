import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class is responsible for choosing who the next coordinator will be.
 */

 public class CoordinatorPicker {

    private String coordinator;
    private String viceCoordinator;

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
    }

    // Randomly selects a faculty member from the list 
    public String pickFacultyMember(List<String> facultyList, Map<String, Integer> membershipTimeMap) {
        Random random = new Random();
        String selected;
        do {
            selected = facultyList.get(random.nextInt(facultyList.size()));
        } while (selected.equals(coordinator) || selected.equals(viceCoordinator) || // Ensures the selected member is not the current coordinator or vice-coordinator
                membershipTimeMap.getOrDefault(selected, 0) < 3); // Checks if the membership time is more than 3

        return selected;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public String getViceCoordinator() {
        return viceCoordinator;
    }
}

