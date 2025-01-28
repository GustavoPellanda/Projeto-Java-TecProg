import java.util.List;
import java.util.Random;

/**
 * This class is responsible for choosing who the next coordinator will be.
 */

public class CoordinatorPicker {

    private String coordinator;
    private String viceCoordinator;

    // Selects the next coordinator and vice-coordinator
    public void chooseCoordinator(List<String> facultyList) {
        if (facultyList == null || facultyList.isEmpty()) {
            return;
        }

        // In the first election, there is no vice-coordinator
        if (viceCoordinator == null) {
            viceCoordinator = pickFacultyMember(facultyList);
            coordinator = pickFacultyMember(facultyList);
        }

        coordinator = viceCoordinator;
        viceCoordinator = pickFacultyMember(facultyList);
    }

    // Randomly selects a faculty member from the list, 
    //ensuring they are not the current coordinator or vice-coordinator.
    public String pickFacultyMember(List<String> facultyList) {
        Random random = new Random();
        String selected;
        do {
            selected = facultyList.get(random.nextInt(facultyList.size()));
        } while (selected.equals(coordinator) || selected.equals(viceCoordinator));

        return selected;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public String getViceCoordinator() {
        return viceCoordinator;
    }
}
