package io.elastic.api;


import javax.json.Json;
import javax.json.JsonObject;
import java.io.Serializable;

/**
 * Represents parameters for a {@link ComponentModule} execution passed
 * to {@link ComponentModule#execute(ExecutionParameters)}.
 */
public final class ExecutionParameters implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Message message;
    private final JsonObject configuration;
    private final JsonObject snapshot;
    private final EventEmitter eventEmitter;

    private ExecutionParameters(final Message message,
                                final EventEmitter eventEmitter,
                                final JsonObject configuration,
                                final JsonObject snapshot) {
        this.message = message;
        this.configuration = configuration;
        this.snapshot = snapshot;
        this.eventEmitter = eventEmitter;
    }

    /**
     * Returns {@link Message} for the component.
     *
     * @return message instance
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Returns component's configuration.
     *
     * @return json object representing component's configuration
     */
    public JsonObject getConfiguration() {
        return configuration;
    }

    /**
     * Returns component's snapshot.
     *
     * @return json object representing component's snapshot
     */
    public JsonObject getSnapshot() {
        return snapshot;
    }

    public EventEmitter getEventEmitter() {
        return eventEmitter;
    }

    /**
     * Used to build {@link ExecutionParameters} instances.
     */
    public static final class Builder {
        private final Message message;
        private JsonObject configuration;
        private JsonObject snapshot;
        private EventEmitter eventEmitter;


        /**
         * Creates a {@link Builder} instance.
         *
         * @param message non-null message for the component
         * @param eventEmitter non-null EventEmitter
         */
        public Builder(final Message message, final EventEmitter eventEmitter) {
            if (message == null) {
                throw new IllegalArgumentException("Message is required");
            }
            if (eventEmitter == null) {
                throw new IllegalArgumentException("EventEmitter is required");
            }

            this.message = message;
            this.eventEmitter = eventEmitter;
            this.configuration = Json.createObjectBuilder().build();
            this.snapshot = Json.createObjectBuilder().build();
        }

        /**
         * Adds component's configuration.
         *
         * @param configuration component's configuration
         * @return this instance
         */
        public Builder configuration(JsonObject configuration) {
            this.configuration = configuration;

            return this;
        }

        /**
         * Adds component's snapshot.
         *
         * @param snapshot component's snapshot
         * @return this instance
         */
        public Builder snapshot(JsonObject snapshot) {
            this.snapshot = snapshot;

            return this;
        }

        /**
         * Builds a {@link ExecutionParameters} instance.
         *
         * @return ExecutionParameters
         */
        public ExecutionParameters build() {
            if (this.configuration == null) {
                throw new IllegalStateException("Configuration may not be null");
            }

            if (this.snapshot == null) {
                throw new IllegalStateException("Snapshot may not be null");
            }

            return new ExecutionParameters(message, eventEmitter, configuration, snapshot);
        }
    }

    @Override
    public String toString() {
        return "ExecutionParameters{" +
                "message=" + message +
                ", configuration=" + configuration +
                ", snapshot=" + snapshot +
                ", eventEmitter=" + eventEmitter +
                '}';
    }
}
