package io.elastic.api;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.ByteArrayInputStream;

/**
 * JSON utilities.
 */
public final class JSON {

    private JSON() {

    }

    /**
     * Parses a String into a {@link JsonObject}.
     *
     * @param input string to parse
     * @return JsonObject
     */
    public static JsonObject parse(String input) {
        if (input == null) {
            return null;
        }

        final JsonReader reader = Json.createReader(
                new ByteArrayInputStream(input.getBytes()));

        return reader.readObject();

    }
}
