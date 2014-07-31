package io.elastic.api;

import com.google.gson.JsonObject;

public final class ExecutionParameters {

    private final Message message;
    private final JsonObject configuration;
    private final JsonObject snapshot;

    private ExecutionParameters(Message message, JsonObject configuration, JsonObject snapshot) {
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

    public JsonObject getSnapshot() {
        return snapshot;
    }

    public static final class Builder {
        private final Message message;
        private JsonObject configuration;
        private JsonObject snapshot;


        public Builder(Message message) {
            if (message == null) {
                throw new IllegalArgumentException("Message is required");
            }

            this.message = message;
            this.configuration = new JsonObject();
            this.snapshot = new JsonObject();
        }

        public Builder configuration(JsonObject configuration) {
            this.configuration = configuration;

            return this;
        }

        public Builder snapshot(JsonObject snapshot) {
            this.snapshot = snapshot;

            return this;
        }

        public ExecutionParameters build() {
            if (this.configuration == null) {
                throw new IllegalStateException("Configuration may not be null");
            }

            if (this.snapshot == null) {
                throw new IllegalStateException("Snapshot may not be null");
            }

            return new ExecutionParameters(message, configuration, snapshot);
        }
    }
}
