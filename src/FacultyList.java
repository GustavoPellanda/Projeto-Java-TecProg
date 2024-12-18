import java.util.ArrayList;
import java.util.List;

/**
 * This class creates a list of faculty members.
 */

public class FacultyList {
    private List<String> facultyList;

    public FacultyList() {
        facultyList = new ArrayList<>();
    }

    public void addFaculty(String name) {
        facultyList.add(name);
    }

    public List<String> getFacultyList() {
        return facultyList;
    }
}
