import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create instances of SaveManager and FacultyList
        SaveManager saveManager = new SaveManager();
        FacultyList facultyList = new FacultyList();

        // Load the saved list
        List<String> savedList = saveManager.loadFacultyList();
        for (String name : savedList) {
            facultyList.addFaculty(name);
        }

        // Create and show the GUI
        GUIManager guiManager = new GUIManager(facultyList);
        guiManager.createAndShowGUI();

        // Save the list automatically when the program is closed
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveManager.saveFacultyList(facultyList.getFacultyList());
        }));
    }
}

