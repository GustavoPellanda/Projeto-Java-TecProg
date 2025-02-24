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
        facultyList.addFaculty("John Doe");
        List<String> faculty = facultyList.getFacultyList();
        assertEquals(1, faculty.size());
        assertEquals("John Doe", faculty.get(0));

        // Test adding a duplicate faculty member
        facultyList.addFaculty("John Doe");
        assertEquals(1, faculty.size()); // Size should remain the same

        // Test adding another faculty member
        facultyList.addFaculty("Jane Smith");
        assertEquals(2, faculty.size());
        assertEquals("Jane Smith", faculty.get(1));
    }

    @Test
    public void testRemoveFaculty() {
        // Add some faculty members
        facultyList.addFaculty("John Doe");
        facultyList.addFaculty("Jane Smith");

        // Test removing an existing faculty member
        boolean removed = facultyList.removeFaculty("John Doe");
        assertTrue(removed);
        List<String> faculty = facultyList.getFacultyList();
        assertEquals(1, faculty.size());
        assertEquals("Jane Smith", faculty.get(0));

        // Test removing a non-existing faculty member
        removed = facultyList.removeFaculty("Non Existing");
        assertFalse(removed);
        assertEquals(1, faculty.size()); // Size should remain the same
    }

    @Test
    public void testIncrementMembershipTime() {
        // Add some faculty members
        facultyList.addFaculty("John Doe");
        facultyList.addFaculty("Jane Smith");

        // Increment membership time
        facultyList.incrementMembershipTime();

        // Check the membership time
        Map<String, Integer> membershipTimeMap = facultyList.getMembershipTime();
        assertEquals(1, membershipTimeMap.get("John Doe"));
        assertEquals(1, membershipTimeMap.get("Jane Smith"));

        // Increment again and check
        facultyList.incrementMembershipTime();
        assertEquals(2, membershipTimeMap.get("John Doe"));
        assertEquals(2, membershipTimeMap.get("Jane Smith"));
    }

    @Test
    public void testSetMembershipTime() {
        // Add a faculty member
        facultyList.addFaculty("John Doe");

        // Set membership time
        facultyList.setMembershipTime("John Doe", 5);

        // Check the membership time
        Map<String, Integer> membershipTimeMap = facultyList.getMembershipTime();
        assertEquals(5, membershipTimeMap.get("John Doe"));

        // Set membership time for a non-existing faculty member
        facultyList.setMembershipTime("Non Existing", 10);
        assertNull(membershipTimeMap.get("Non Existing")); // Should not affect the map
    }

    @Test
    public void testFormatName() {
        // Add a faculty member with mixed case and extra spaces
        facultyList.addFaculty("  john  DOE  ");

        // Check if the name is formatted correctly
        List<String> faculty = facultyList.getFacultyList();
        assertEquals("John Doe", faculty.get(0));
    }
}