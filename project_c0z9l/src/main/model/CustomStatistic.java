package model;

import org.json.JSONObject;
import persistence.Writable;

// The class to represent a custom statistic of the users choice that can be added to the user information
public class CustomStatistic implements Writable {
    private String name;
    private String unit;
    private int value;

    // EFFECTS: Creates custom statistic with given name, unit, and value
    public CustomStatistic(String name, String unit, int value) {
        this.name = name;
        this.unit = unit;
        this.value = value;
    }

    // EFFECTS: Creates custom statistic with given name and value
    public CustomStatistic(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getUnit() {
        if (this.unit == null) {
            return "";
        } else {
            return this.unit;
        }
    }

    public int getValue() {
        return this.value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setValue(int value) {
        this.value = value;
    }

    // MODIFIES: this
    // EFFECTS: same as setValue but also logs event
    public void changeValue(int value, String name) {
        int originalValue = this.value;
        int newValue = value;
        this.value = value;
        EventLog.getInstance().logEvent(new Event("Changed the value of \"" + name + "\" from "
                + originalValue + " to " + newValue + "."));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        if (unit == null) {
            json.put("unit", "thisIsNull");
        } else {
            json.put("unit", unit);
        }
        json.put("value", value);
        return json;
    }
}
