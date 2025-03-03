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
        facultyList.addFaculty("Claude Shannon");
        assertEquals(2, faculty.size());
        assertEquals("Claude Shannon", faculty.get(1));

        // Test adding more faculty members
        facultyList.addFaculty("Donald Knuth");
        facultyList.addFaculty("John Von Neumann");
        facultyList.addFaculty("Tim Berners-Lee");
        assertEquals(5, faculty.size());
        assertEquals("Donald Knuth", faculty.get(2));
        assertEquals("John Von Neumann", faculty.get(3));
        assertEquals("Tim Berners-Lee", faculty.get(4));
    }

    @Test
    public void testRemoveFaculty() {
        // Add some faculty members
        facultyList.addFaculty("Alan Turing");
        facultyList.addFaculty("Claude Shannon");
        facultyList.addFaculty("Donald Knuth");

        // Test removing an existing faculty member
        boolean removed = facultyList.removeFaculty("Alan Turing");
        assertTrue(removed);
        List<String> faculty = facultyList.getFacultyList();
        assertEquals(2, faculty.size());
        assertEquals("Claude Shannon", faculty.get(0));
        assertEquals("Donald Knuth", faculty.get(1));

        // Test removing a non-existing faculty member
        removed = facultyList.removeFaculty("Non Existing");
        assertFalse(removed);
        assertEquals(2, faculty.size()); // Size should remain the same
    }

    @Test
    public void testIncrementMembershipTime() {
        // Add some faculty members
        facultyList.addFaculty("Alan Turing");
        facultyList.addFaculty("Claude Shannon");
        facultyList.addFaculty("Donald Knuth");

        // Increment membership time
        facultyList.incrementMembershipTime();

        // Check the membership time
        Map<String, Integer> membershipTimeMap = facultyList.getMembershipTime();
        assertEquals(1, membershipTimeMap.get("Alan Turing"));
        assertEquals(1, membershipTimeMap.get("Claude Shannon"));
        assertEquals(1, membershipTimeMap.get("Donald Knuth"));

        // Increment again and check
        facultyList.incrementMembershipTime();
        assertEquals(2, membershipTimeMap.get("Alan Turing"));
        assertEquals(2, membershipTimeMap.get("Claude Shannon"));
        assertEquals(2, membershipTimeMap.get("Donald Knuth"));
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

        // Test formatting for another faculty member
        facultyList.addFaculty("  claude  SHANNON  ");
        assertEquals("Claude Shannon", faculty.get(1));
    }
}