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
    private void loadSavedFacultyList() throws AppException {
        try {
            Map<String, Integer> savedMap = saveManager.loadFacultyList();
            for (Map.Entry<String, Integer> entry : savedMap.entrySet()) {
                String name = entry.getKey();
                int membershipTime = entry.getValue();

                // Adds the faculty member and sets the corresponding membership time
                facultyList.addFaculty(name);
                facultyList.setMembershipTime(name, membershipTime);
            }
        } catch (Exception e) {
            throw new AppException("Failed to load faculty list", e);
        }
    }

    // Counts the years for the app and faculty members
    private void changeTimeStamps() throws AppException {
        try {
            facultyList.incrementMembershipTime(); // At the moment, the year is incremented every time the application is started.
        } catch (Exception e) {
            throw new AppException("Failed to increment membership time", e);
        }
    }

    // Creates the GUI
    private void createAndShowGUI() throws AppException {
        try {
            guiManager.createAndShowGUI();
        } catch (Exception e) {
            throw new AppException("Failed to create and show GUI", e);
        }
    }

    // Saves the faculty list when the application is closed
    private void Shutdown() throws AppException {
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    saveManager.saveFacultyList(facultyList.getMembershipTime());
                } catch (Exception e) {
                    System.err.println("Failed to save faculty list during shutdown: " + e.getMessage());
                }
            }));
        } catch (Exception e) {
            throw new AppException("Failed to set up shutdown hook", e);
        }
    }

    public void start() {
        try {
            loadSavedFacultyList();
            changeTimeStamps();
            createAndShowGUI();
            Shutdown();
        } catch (AppException e) {
            System.err.println("Application error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}