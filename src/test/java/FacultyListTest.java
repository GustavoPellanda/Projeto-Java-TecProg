import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;

public class FacultyListTest {

    private FacultyList facultyList;

    @BeforeEach
    public void setUp() {
        facultyList = new FacultyList();
    }

    @Test
    public void testAddFaculty() {
        // Test adding a new faculty member
        facultyList.addFaculty("Alan Turing");
        List<String> faculty = facultyList.getFacultyList();
        assertEquals(1, faculty.size());
        assertEquals("Alan Turing", faculty.get(0));

        // Test adding a duplicate faculty member
        facultyList.addFaculty("Alan Turing");
        assertEquals(1, faculty.size()); // Size should remain the same

        // Test adding another faculty member
        facultyList.addFaculty("Richard Feynman");
        assertEquals(2, faculty.size());
        assertEquals("Richard Feynman", faculty.get(1));
    }

    @Test
    public void testRemoveFaculty() {
        // Add some faculty members
        facultyList.addFaculty("Alan Turing");
        facultyList.addFaculty("Richard Feynman");

        // Test removing an existing faculty member
        boolean removed = facultyList.removeFaculty("Alan Turing");
        assertTrue(removed);
        List<String> faculty = facultyList.getFacultyList();
        assertEquals(1, faculty.size());
        assertEquals("Richard Feynman", faculty.get(0));

        // Test removing a non-existing faculty member
        removed = facultyList.removeFaculty("Non Existing");
        assertFalse(removed);
        assertEquals(1, faculty.size()); // Size should remain the same
    }

    @Test
    public void testIncrementMembershipTime() {
        // Add some faculty members
        facultyList.addFaculty("Alan Turing");
        facultyList.addFaculty("Richard Feynman");

        // Increment membership time
        facultyList.incrementMembershipTime();

        // Check the membership time
        Map<String, Integer> membershipTimeMap = facultyList.getMembershipTime();
        assertEquals(1, membershipTimeMap.get("Alan Turing"));
        assertEquals(1, membershipTimeMap.get("Richard Feynman"));

        // Increment again and check
        facultyList.incrementMembershipTime();
        assertEquals(2, membershipTimeMap.get("Alan Turing"));
        assertEquals(2, membershipTimeMap.get("Richard Feynman"));
    }

    @Test
    public void testSetMembershipTime() {
        // Add a faculty member
        facultyList.addFaculty("Alan Turing");

        // Set membership time
        facultyList.setMembershipTime("Alan Turing", 5);

        // Check the membership time
        Map<String, Integer> membershipTimeMap = facultyList.getMembershipTime();
        assertEquals(5, membershipTimeMap.get("Alan Turing"));

        // Set membership time for a non-existing faculty member
        facultyList.setMembershipTime("Non Existing", 10);
        assertNull(membershipTimeMap.get("Non Existing")); // Should not affect the map
    }

    @Test
    public void testFormatName() {
        // Add a faculty member with mixed case and extra spaces
        facultyList.addFaculty("  alan  TURING  ");

        // Check if the name is formatted correctly
        List<String> faculty = facultyList.getFacultyList();
        assertEquals("Alan Turing", faculty.get(0));
    }
}