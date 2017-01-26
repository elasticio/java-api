package io.elastic.api;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

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

    /**
     * Writes a {@link JsonObject} into a String and returns it.
     *
     * @param object object to stringify
     * @return String representation of the object
     */
    public static String stringify(final JsonObject object) {
        final StringWriter writer = new StringWriter();

        final JsonWriter jsonWriter = Json.createWriter(writer);
        jsonWriter.writeObject(object);
        jsonWriter.close();

        return writer.toString();
    }
}
