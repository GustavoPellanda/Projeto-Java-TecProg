import java.io.*;
import java.util.*;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * This class is responsible for calculating the probability of a next coordinator being chosen.
 */

public class ProbabilityCalculator {

    // Method to read faculty members and their membership time from the JSON file
    public Map<String, Integer> readFacultyList(String filePath) {
        Map<String, Integer> facultyMap = new HashMap<>();
        try (FileReader reader = new FileReader(filePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject jsonObject = new JSONObject(tokener);

            // Iterate through the JSON object and fill the facultyMap
            for (String name : jsonObject.keySet()) {
                facultyMap.put(name, jsonObject.getInt(name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return facultyMap;
    }

    // Method to read coordinators from the coordinators.txt file
    public List<String> readCoordinators(String filePath) {
        List<String> coordinators = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                coordinators.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coordinators;
    }

    // Method to calculate the selection probability for each faculty member
    public Map<String, Double> calculateProbabilities(Map<String, Integer> facultyList, List<String> coordinators) {
        Map<String, Double> probabilities = new HashMap<>();

        // Filter out eligible candidates (those with at least 3 years of membership and who have not been a coordinator)
        int eligibleCount = 0;
        for (String faculty : facultyList.keySet()) {
            if (facultyList.get(faculty) >= 3 && !coordinators.contains(faculty)) {
                eligibleCount++;
            }
        }

        // If there are no eligible candidates, return an empty map
        if (eligibleCount == 0) {
            return probabilities;
        }

        // Calculate the probability for each eligible member
        double probability = 1.0 / eligibleCount;
        for (String faculty : facultyList.keySet()) {
            if (facultyList.get(faculty) >= 3 && !coordinators.contains(faculty)) {
                probabilities.put(faculty, probability);
            }
        }

        return probabilities;
    }

    // Method to print the probabilities
    public void printProbabilities(Map<String, Double> probabilities) {
        System.out.println("Faculty Member - Probability");
        for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
            System.out.printf("%s - %.4f\n", entry.getKey(), entry.getValue());
        }
    }
}
