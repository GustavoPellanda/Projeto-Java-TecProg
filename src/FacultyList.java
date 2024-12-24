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
        facultyList.add(name);
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
}
