import java.util.List;

/**
 * This class is responsible for managing the order of execution of the application.
 */

public class App {
    private FacultyList facultyList;
    private GUIManager guiManager;
    private SaveManager saveManager;
    private TimeCounter timeCounter;

    public App() {
        facultyList = new FacultyList();
        guiManager = new GUIManager(facultyList);
        saveManager = new SaveManager();
        timeCounter = new TimeCounter();
    }

    // Loads the saved faculty list into the facultyList
    private void loadSavedFacultyList() {
        List<String> savedList = saveManager.loadFacultyList();
        for (String name : savedList) {
            facultyList.addFaculty(name);
        }
    }

    // Counts the years for the app and faculty members
    private void changeTimeStamps() {
        timeCounter.incrementYear(); // At the moment, the year is incremented every time the application is started.
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
        changeTimeStamps();
        loadSavedFacultyList();
        createAndShowGUI();
        setupShutdownHook();
    }
}
