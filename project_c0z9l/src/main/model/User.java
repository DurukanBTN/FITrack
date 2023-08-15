package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Scanner;

// The class to represent the user
public class User implements Writable {
    private String name;
    private int weight;
    private int height;
    private int calorie;
    private ArrayList<CustomStatistic> customStatistics = new ArrayList<>();
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private ArrayList<Day> calender = new ArrayList<>();

    //EFFECTS: construct user with given name, weight, height, and calorie intake
    public User(String name, int weight, int height, int calorie) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.calorie = calorie;
    }

    //EFFECTS: construct user with null fields.
    public User() {}

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public String getName() {
        return this.name;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getHeight() {
        return this.height;
    }

    public int getCalorie() {
        return this.calorie;
    }

    public ArrayList<Day> getCalender() {
        return this.calender;
    }

    public ArrayList<Exercise> getExercises() {
        return this.exercises;
    }

    public ArrayList<CustomStatistic> getCustomStatistics() {
        return this.customStatistics;
    }

    public CustomStatistic getCustomStatistic(int index) {
        return this.customStatistics.get(index);
    }

    public ArrayList<String> getCustomStatisticsNames() {
        ArrayList<String> names = new ArrayList<>();
        for (CustomStatistic cs : getCustomStatistics()) {
            names.add(cs.getName());
        }
        return names;
    }

    public CustomStatistic getCustomStatisticFromName(String name) {
        return getCustomStatistic(getCustomStatisticsNames().indexOf(name));
    }

    // REQUIRES: the exercises list doesn't already contain the exercise
    // MODIFIES: this
    // EFFECTS: adds the given exercise to the exercises list
    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }

    // REQUIRES: the custom statistic list doesn't already contain the statistic
    // MODIFIES: this
    // EFFECTS: adds the given custom statistic to the custom statistics list
    public void addCustomStatistic(CustomStatistic cs) {
        this.customStatistics.add(cs);
        EventLog.getInstance().logEvent(new Event("Added the custom statistic \"" + cs.getName()
                + "\" that has a value of " + cs.getValue() + cs.getUnit() + "."));
    }

    // MODIFIES: this
    // EFFECTS: clears the exercises list
    public void resetExercises() {
        this.exercises = new ArrayList<>();
    }

    // EFFECTS: returns a list of the names of the current exercises
    public ArrayList<String> getExerciseNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Exercise exercise : getExercises()) {
            names.add(exercise.getName());
        }
        return names;
    }

    public Exercise getExercise(int index) {
        return this.exercises.get(index);
    }

    public Exercise getExerciseFromName(String name) {
        return getExercise(getExerciseNames().indexOf(name));
    }

    // MODIFIES: this
    // EFFECTS: saves all the current information of a user as a day so that it can be accessed later
    public void addCurrentDay() {
        User clonedUser = this.cloneUser();
        this.getCalender().add(new Day(this.getCalender().size() + 1, clonedUser));
    }

    // EFFECTS: creates another user instance with the same fields of this user instance, and returns the new user
    public User cloneUser() {
        User clonedUser = new User(name, weight, height, calorie);
        for (CustomStatistic cs : this.getCustomStatistics()) {
            CustomStatistic customStatistic;
            if (cs.getUnit().equalsIgnoreCase("")) {
                customStatistic = new CustomStatistic(cs.getName(), cs.getValue());
            } else {
                customStatistic = new CustomStatistic(cs.getName(), cs.getUnit(), cs.getValue());
            }
            clonedUser.getCustomStatistics().add(customStatistic);
        }
        for (Exercise exercise : this.getExercises()) {
            clonedUser.getExercises().add(exercise);
        }
        return clonedUser;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("weight", weight);
        json.put("height", height);
        json.put("calorie", calorie);
        json.put("customStatistics", customStatisticsToJson());
        json.put("exercises", exercisesToJson());
        json.put("calender", calenderToJson());
        return json;
    }

    // EFFECTS: returns the days in the calender as a JSON array
    private JSONArray calenderToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Day day: calender) {
            jsonArray.put(day.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns the exercises in the array as a JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise exercise: exercises) {
            jsonArray.put(exercise.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns the custom statistics in the array as a JSON array
    private JSONArray customStatisticsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (CustomStatistic cs: customStatistics) {
            jsonArray.put(cs.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: adds the given day to the calender
    public void addDay(int number, User userD) {
        calender.add(new Day(number, userD));
    }

    public void removeCustomStatistic(int index) {
        CustomStatistic cs = customStatistics.get(index);
        EventLog.getInstance().logEvent(new Event("Removed the custom statistic \"" + cs.getName()
                + "\"."));
        customStatistics.remove(index);
    }
}
