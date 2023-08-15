package persistence;

import model.CustomStatistic;
import model.Day;
import model.Exercise;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads user from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads user from file and returns it;
    // throws IOException if an error occurs reading data from file
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUser(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses user from JSON object and returns it
    private User parseUser(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int weight = jsonObject.getInt("weight");
        int height = jsonObject.getInt("height");
        int calorie = jsonObject.getInt("calorie");
        User user = new User(name, weight, height, calorie);
        addCustomStatistics(user, jsonObject);
        addExercises(user, jsonObject);
        addCalender(user, jsonObject);
        return user;
    }

    // MODIFIES: user
    // EFFECTS: parses days from JSON object and adds it to user
    private void addCalender(User user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("calender");
        for (Object json : jsonArray) {
            JSONObject day = (JSONObject) json;
            addDay(user, day);
        }
    }

    // MODIFIES: user
    // EFFECTS: parses day from JSON object and adds it to calender
    private void addDay(User user, JSONObject jsonObject) {
        int number = jsonObject.getInt("number");
        String name = jsonObject.getString("name");
        int weight = jsonObject.getInt("weight");
        int height = jsonObject.getInt("height");
        int calorie = jsonObject.getInt("calorie");
        User userD = new User(name, weight, height, calorie);
        addCustomStatistics(userD, jsonObject);
        addExercises(userD, jsonObject); // !!!!!!!
        user.addDay(number, userD);
    }

    // MODIFIES: user
    // EFFECTS: parses exercises from JSON object and adds it to user
    private void addExercises(User user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("exercises");
        for (Object json : jsonArray) {
            JSONObject exercise = (JSONObject) json;
            addExercise(user, exercise);
        }
    }

    // MODIFIES: user
    // EFFECTS: parses exercise from JSON object and adds it to exercises
    private void addExercise(User user, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int weight = jsonObject.getInt("weight");
        int rep = jsonObject.getInt("rep");
        Exercise exercise = new Exercise(name, weight, rep);
        user.addExercise(exercise);
    }

    // MODIFIES: user
    // EFFECTS: parses thingies from JSON object and adds them to user
    private void addCustomStatistics(User user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("customStatistics");
        for (Object json : jsonArray) {
            JSONObject nextStatistic = (JSONObject) json;
            addStatistic(user, nextStatistic);
        }
    }

    // MODIFIES: user
    // EFFECTS: parses thingy from JSON object and adds it to user
    private void addStatistic(User user, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String unit = jsonObject.getString("unit");
        int value = jsonObject.getInt("value");
        CustomStatistic customStatistic;
        if (unit.equalsIgnoreCase("thisIsNull")) {
            customStatistic = new CustomStatistic(name, value);
        } else {
            customStatistic = new CustomStatistic(name, unit, value);
        }
        user.getCustomStatistics().add(customStatistic);
    }
}
