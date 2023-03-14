package sg.edu.nus.iss.workshop16.model;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Type implements Serializable {
    private String type;
    private int count;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    // method to convert to JSON
    public JsonObjectBuilder toJSON() {
        return Json.createObjectBuilder()
                .add("type", this.getType())
                .add("count", this.getCount());
    }

    public static Type createFromJSON(JsonObject jsObj) {
        Type t = new Type();
        String type = jsObj.getString("type");
        JsonNumber count = jsObj.getJsonNumber("count");
        t.setType(type);
        t.setCount(count.intValue());
        return t;
    }

}
