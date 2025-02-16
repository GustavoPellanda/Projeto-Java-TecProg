import java.util.Map;

/**
 * This class is responsible for managing the order of execution of the application.
 */

public class App {
    private FacultyList facultyList;
    private GUIManager guiManager;
    private SaveManager saveManager;
    private CoordinatorPicker coordinatorPicker;

    public App() {
        facultyList = new FacultyList();
        coordinatorPicker = new CoordinatorPicker();
        guiManager = new GUIManager(facultyList, coordinatorPicker);
        saveManager = new SaveManager();
    }

    // Loads the saved faculty list into the facultyList
    private void loadSavedFacultyList() {
        Map<String, Integer> savedMap = saveManager.loadFacultyList();
        for (Map.Entry<String, Integer> entry : savedMap.entrySet()) {
            String name = entry.getKey();
            int membershipTime = entry.getValue();
    
            // Adds the faculty member and sets the corresponding membership time
            facultyList.addFaculty(name);
            facultyList.setMembershipTime(name, membershipTime);
        }
    }

    // Counts the years for the app and faculty members
    private void changeTimeStamps() {
        facultyList.incrementMembershipTime(); // At the moment, the year is incremented every time the application is started.
    }

    // Creates the GUI
    private void createAndShowGUI() {
        guiManager.createAndShowGUI();
    }

    // Saves the faculty list when the application is closed
    private void Shutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveManager.saveFacultyList(facultyList.getMembershipTime());
        }));
    }

    public void start() {
        loadSavedFacultyList();
        changeTimeStamps();
        createAndShowGUI();
        Shutdown();
    }
}
