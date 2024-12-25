import java.util.List;

/**
 * This class is responsible for managing the order of execution of the application.
 */

public class App {
    private FacultyList facultyList;
    private GUIManager guiManager;
    private SaveManager saveManager;

    public App() {
        saveManager = new SaveManager();
        facultyList = new FacultyList();
        guiManager = new GUIManager(facultyList);
    }

    // Loads the saved faculty list into the facultyList
    private void loadSavedFacultyList() {
        List<String> savedList = saveManager.loadFacultyList();
        for (String name : savedList) {
            facultyList.addFaculty(name);
        }
    }

    // Creates the GUI
    private void createAndShowGUI() {
        guiManager.createAndShowGUI();
    }

    // Saves the faculty list when the application is closed
    private void setupShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveManager.saveFacultyList(facultyList.getFacultyList());
        }));
    }

    public void start() {
        loadSavedFacultyList();
        createAndShowGUI();
        setupShutdownHook();
    }
}
