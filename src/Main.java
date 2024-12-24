public class Main {
    public static void main(String[] args) {
        FacultyList facultyList = new FacultyList();
        GUIManager guiManager = new GUIManager(facultyList);
        guiManager.createAndShowGUI();
    }
}
