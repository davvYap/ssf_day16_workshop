package sg.edu.nus.iss.workshop16.model;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Rulebook implements Serializable {

    private int total_count;
    private String file;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    // method to convert to JSON
    public JsonObjectBuilder toJSON() {
        return Json.createObjectBuilder()
                .add("total_count", this.getTotal_count())
                .add("file", this.getFile());
    }

    public static Rulebook createFromJSON(JsonObject jsObj) {
        Rulebook rb = new Rulebook();
        String file = jsObj.getString("file");
        JsonNumber total_count = jsObj.getJsonNumber("total_count");
        rb.setFile(file);
        rb.setTotal_count(total_count.intValue());
        return rb;
    }

}
