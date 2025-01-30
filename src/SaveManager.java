import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for saving and loading the faculty list and their membership time.
 */

public class SaveManager {
    private static final String FILE_NAME = "faculty_list.json";

    // Save the faculty list and membership time as JSON manually
    public void saveFacultyList(Map<String, Integer> facultyMap) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write(toJson(facultyMap));
        } catch (IOException e) {
            System.err.println("Error saving faculty list: " + e.getMessage());
        }
    }

    // Load the faculty list and membership time from JSON manually
    public Map<String, Integer> loadFacultyList() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new HashMap<>();
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

        return new HashMap<>();
    }

    // Convert the faculty map to JSON (manual)
    private String toJson(Map<String, Integer> facultyMap) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        int i = 0;
        for (Map.Entry<String, Integer> entry : facultyMap.entrySet()) {
            jsonBuilder.append("\"").append(entry.getKey()).append("\":").append(entry.getValue());
            if (i < facultyMap.size() - 1) {
                jsonBuilder.append(",");
            }
            i++;
        }
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    // Convert JSON to the faculty map (manual)
    private Map<String, Integer> fromJson(String json) {
        Map<String, Integer> facultyMap = new HashMap<>();
        // Remove the curly braces and split the entries
        String cleanedJson = json.replace("{", "").replace("}", "").replace("\"", "");
        if (!cleanedJson.isEmpty()) {
            String[] entries = cleanedJson.split(",");
            for (String entry : entries) {
                String[] keyValue = entry.split(":");
                if (keyValue.length == 2) {
                    String name = keyValue[0].trim();
                    int membershipTime = Integer.parseInt(keyValue[1].trim());
                    facultyMap.put(name, membershipTime);
                }
            }
        }
        return facultyMap;
    }
}