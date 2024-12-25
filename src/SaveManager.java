import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for saving and loading the faculty list.
 */

public class SaveManager {
    private static final String FILE_NAME = "faculty_list.json";

    // Save the list as JSON manually
    public void saveFacultyList(List<String> facultyList) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write(toJson(facultyList));
        } catch (IOException e) {
            System.err.println("Error saving faculty list: " + e.getMessage());
        }
    }

    // Load the list from JSON manually
    public List<String> loadFacultyList() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            return fromJson(jsonBuilder.toString());
        } catch (IOException e) {
            System.err.println("Error loading faculty list: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    // Convert list to JSON (manual)
    private String toJson(List<String> facultyList) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        for (int i = 0; i < facultyList.size(); i++) {
            jsonBuilder.append("\"").append(facultyList.get(i)).append("\"");
            if (i < facultyList.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }

    // Convert JSON to list (manual)
    private List<String> fromJson(String json) {
        List<String> facultyList = new ArrayList<>();
        // Remove the brackets and split the names
        String cleanedJson = json.replace("[", "").replace("]", "").replace("\"", "");
        if (!cleanedJson.isEmpty()) {
            String[] names = cleanedJson.split(",");
            for (String name : names) {
                facultyList.add(name.trim());
            }
        }
        return facultyList;
    }
}
