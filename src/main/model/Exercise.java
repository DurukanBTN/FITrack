package model;

import org.json.JSONObject;
import persistence.Writable;

// The class to represent an exercise
public class Exercise implements Writable {
    private String name;
    private int weight;
    private int rep;

    // EFFECTS: Creates exercise with given name, weight, and repetition number
    public Exercise(String name, int weight, int rep) {
        this.name = name;
        this.weight = weight;
        this.rep = rep;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getRep() {
        return rep;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("weight", weight);
        json.put("rep", rep);
        return json;
    }
}
