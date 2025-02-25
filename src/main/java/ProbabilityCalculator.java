import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.json.JSONObject;

/**
 * This class is responsible for calculating the probability of a next coordinator being chosen.
 */

public class ProbabilityCalculator {
    private static final String COORDINATORS_FILE = "coordinators.txt";
    private static final String FACULTY_FILE = "faculty_list.json";

    public static Map<String, Double> calculateProbabilities() {
        Map<String, Integer> facultyMap = loadFacultyList();
        Set<String> pastCoordinators = loadPastCoordinators();
        List<String> eligibleMembers = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : facultyMap.entrySet()) {
            String name = entry.getKey();
            int years = entry.getValue();
            if (years >= 3 && !pastCoordinators.contains(name)) {
                eligibleMembers.add(name);
            }
        }

        Map<String, Double> probabilities = new HashMap<>();
        int totalEligible = eligibleMembers.size();
        
        for (String name : eligibleMembers) {
            probabilities.put(name, 1.0 / totalEligible);
        }
        
        return probabilities;
    }

    private static Map<String, Integer> loadFacultyList() {
        Map<String, Integer> facultyMap = new HashMap<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(FACULTY_FILE)));
            JSONObject jsonObject = new JSONObject(content);
            for (String key : jsonObject.keySet()) {
                facultyMap.put(key, jsonObject.getInt(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return facultyMap;
    }

    private static Set<String> loadPastCoordinators() {
        Set<String> pastCoordinators = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(COORDINATORS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                pastCoordinators.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pastCoordinators;
    }
}
