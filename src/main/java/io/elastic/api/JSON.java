package io.elastic.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

public final class JSON {

    private JSON() {

    }

    public static JsonObject parse(String input) {
        if (input == null) {
            return null;
        }

        final Gson gson = new Gson();

        final JsonElement json = gson.fromJson(input, (Type) JsonObject.class);

        if (!json.isJsonObject()) {
            throw new IllegalArgumentException(String.format("'%s' cannot be parsed to a JsonObject", input));
        }

        return (JsonObject)json;
    }
}
