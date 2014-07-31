package io.elastic.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public final class ExecutionParameters {

    private final Message message;
    private final JsonObject configuration;
    private final JsonElement snapshot;

    public ExecutionParameters(Message message, JsonObject configuration, JsonElement snapshot) {
        this.message = message;
        this.configuration = configuration;
        this.snapshot = snapshot;
    }

    public Message getMessage() {
        return message;
    }

    public JsonObject getConfiguration() {
        return configuration;
    }

    public JsonElement getSnapshot() {
        return snapshot;
    }
}
