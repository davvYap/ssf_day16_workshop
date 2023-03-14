package sg.edu.nus.iss.workshop16.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class json {
    JsonObject boardObj = Json.createObjectBuilder()
            .add("name", "Mastermind")
            .add("pieces", Json.createObjectBuilder()
                    .add("decoding_board", Json.createObjectBuilder()
                            .add("total_count", 1))
                    .add("pegs", Json.createObjectBuilder()
                            .add("total_count", 102)
                            .add("types", Json.createArrayBuilder()
                                    .add(Json.createObjectBuilder()
                                            .add("type", "code")
                                            .add("count", 72))

                                    .add(Json.createObjectBuilder()
                                            .add("type", "key")
                                            .add("count", 30))))
                    .add("rulebook", Json.createObjectBuilder()
                            .add("total_count", 1))
                    .add("file", "rulebook-ultimate-mastermind.pdf"))
            .build();
}
