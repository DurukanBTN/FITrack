package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// The class to represent a day (in the calendar). Every day has a copy of the user information that were present when
// that day was saved.
public class Day implements Writable {
    private int number;
    private User user;

    // EFFECTS: Creates day with given number and user information
    public Day(int number, User user) {
        this.number = number;
        this.user = user;
    }

    public int getNumber() {
        return this.number;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("number", number);
        json.put("name", user.getName());
        json.put("weight", user.getWeight());
        json.put("height", user.getHeight());
        json.put("calorie", user.getCalorie());
        json.put("customStatistics", customStatisticsToJson());
        json.put("exercises", exercisesToJson());
        return json;
    }

    // EFFECTS: returns the exercises in the array as a JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise exercise: user.getExercises()) {
            jsonArray.put(exercise.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns the custom statistics in the array as a JSON array
    private JSONArray customStatisticsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (CustomStatistic cs: user.getCustomStatistics()) {
            jsonArray.put(cs.toJson());
        }

        return jsonArray;
    }
}
