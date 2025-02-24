import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CoordinatorPickerTest {

    private CoordinatorPicker coordinatorPicker;
    private List<String> facultyList;
    private Map<String, Integer> membershipTimeMap;

    @BeforeEach
    void setUp() {
        coordinatorPicker = new CoordinatorPicker();
        facultyList = Arrays.asList(
            "Alan Turing",
            "Claude Shannon",
            "Donald Knuth",
            "James Clerk Maxwell",
            "John Von Neumann",
            "José Leite Lopes",
            "Max Planck",
            "Murray Gell-Mann",
            "Mário Schenberg",
            "Niels Bohr",
            "Richard Feynman",
            "Tim Berners-Lee"
        );

        membershipTimeMap = new HashMap<>();
        membershipTimeMap.put("Alan Turing", 5);
        membershipTimeMap.put("Claude Shannon", 4);
        membershipTimeMap.put("Donald Knuth", 6);
        membershipTimeMap.put("James Clerk Maxwell", 3);
        membershipTimeMap.put("John Von Neumann", 7);
        membershipTimeMap.put("José Leite Lopes", 4);
        membershipTimeMap.put("Max Planck", 8);
        membershipTimeMap.put("Murray Gell-Mann", 5);
        membershipTimeMap.put("Mário Schenberg", 6);
        membershipTimeMap.put("Niels Bohr", 9);
        membershipTimeMap.put("Richard Feynman", 10);
        membershipTimeMap.put("Tim Berners-Lee", 4);

        // Ensure the coordinators file is empty before each test
        try {
            Files.deleteIfExists(Paths.get("coordinators.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        // Clean up the coordinators file after each test
        try {
            Files.deleteIfExists(Paths.get("coordinators.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testChooseCoordinator() {
        coordinatorPicker.chooseCoordinator(facultyList, membershipTimeMap);
        String coordinator = coordinatorPicker.getCoordinator();
        String viceCoordinator = coordinatorPicker.getViceCoordinator();

        assertNotNull(coordinator);
        assertNotNull(viceCoordinator);
        assertNotEquals(coordinator, viceCoordinator);
        assertTrue(facultyList.contains(coordinator));
        assertTrue(facultyList.contains(viceCoordinator));
    }

    @Test
    void testPickFacultyMember() {
        String pickedMember = coordinatorPicker.pickFacultyMember(facultyList, membershipTimeMap);
        assertNotNull(pickedMember);
        assertTrue(facultyList.contains(pickedMember));
        assertTrue(membershipTimeMap.getOrDefault(pickedMember, 0) >= 3);
    }

    @Test
    void testSaveCoordinatorOnList() {
        String coordinator = "Alan Turing";
        coordinatorPicker.saveCoordinatorOnList(coordinator);

        File file = new File("coordinators.txt");
        assertTrue(file.exists());

        try {
            List<String> lines = Files.readAllLines(Paths.get("coordinators.txt"));
            assertTrue(lines.contains(coordinator));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testIsCoordinatorOnList() {
        String coordinator = "Richard Feynman";
        coordinatorPicker.saveCoordinatorOnList(coordinator);

        assertTrue(coordinatorPicker.isCoordinatorOnList(coordinator));
        assertFalse(coordinatorPicker.isCoordinatorOnList("Tim Berners-Lee"));
    }

    @Test
    void testChooseCoordinatorWithEmptyList() {
        coordinatorPicker.chooseCoordinator(Arrays.asList(), membershipTimeMap);
        assertEquals("", coordinatorPicker.getCoordinator());
        assertEquals("", coordinatorPicker.getViceCoordinator());
    }

    @Test
    void testChooseCoordinatorWithNullList() {
        coordinatorPicker.chooseCoordinator(null, membershipTimeMap);
        assertEquals("", coordinatorPicker.getCoordinator());
        assertEquals("", coordinatorPicker.getViceCoordinator());
    }
}