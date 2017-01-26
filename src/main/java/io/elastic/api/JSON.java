package io.elastic.api;


import javax.json.*;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.concurrent.Callable;

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
    public static JsonObject parseObject(String input) {
        final JsonReader reader = createReader(input);

        if (reader == null) {
            return null;
        }

        try {
            return reader.readObject();
        } finally {
            reader.close();
        }
    }

    /**
     * Parses a String into a {@link JsonArray}.
     *
     * @param input string to parse
     * @return JsonArray
     */
    public static JsonArray parseArray(String input) {
        final JsonReader reader = createReader(input);

        if (reader == null) {
            return null;
        }

        try {
            return reader.readArray();
        } finally {
            reader.close();
        }
    }

    private static <T> JsonReader createReader(final String input) {
        if (input == null) {
            return null;
        }

        final JsonReader reader = Json.createReader(
                new ByteArrayInputStream(input.getBytes()));

        return reader;
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
