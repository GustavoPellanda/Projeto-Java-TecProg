import java.util.Map;

/**
 * This class is responsible for managing the order of execution of the application.
 */

public class App {
    private FacultyList facultyList;
    private GUIManager guiManager;
    private SaveManager saveManager;
    private TimeCounter timeCounter;
    private CoordinatorPicker coordinatorPicker;

    public App() {
        facultyList = new FacultyList();
        coordinatorPicker = new CoordinatorPicker();
        guiManager = new GUIManager(facultyList, coordinatorPicker);
        saveManager = new SaveManager();
        timeCounter = new TimeCounter();
    }

    // Loads the saved faculty list into the facultyList
    private void loadSavedFacultyList() {
        Map<String, Integer> savedMap = saveManager.loadFacultyList();
        for (Map.Entry<String, Integer> entry : savedMap.entrySet()) {
            facultyList.addFaculty(entry.getKey()); // Adds the faculty member
            for (int i = 0; i < entry.getValue(); i++) {
                facultyList.incrementMembership(); // Increments membership time to match saved value
            }
        }
    }

    // Counts the years for the app and faculty members
    private void changeTimeStamps() {
        timeCounter.incrementYear(); // At the moment, the year is incremented every time the application is started.
        facultyList.incrementMembership();
    }

    // Creates the GUI
    private void createAndShowGUI() {
        guiManager.createAndShowGUI();
    }

    // Saves the faculty list when the application is closed
    private void Shutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveManager.saveFacultyList(facultyList.getMembershipTimeMap());
        }));
    }

    public void start() {
        changeTimeStamps();
        loadSavedFacultyList();
        createAndShowGUI();
        Shutdown();
    }
}
