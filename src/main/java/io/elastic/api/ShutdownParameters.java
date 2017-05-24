package io.elastic.api;

import javax.json.JsonObject;
import java.io.Serializable;

/**
 * Represents parameters for a {@link Module#shutdown(ShutdownParameters)} method.
 */
public final class ShutdownParameters implements Serializable {

    private static final long serialVersionUID = 1L;

    private final JsonObject configuration;
    private final JsonObject state;

    public ShutdownParameters(final JsonObject configuration, final JsonObject state) {
        this.configuration = configuration;
        this.state = state;
    }

    /**
     * Returns module's configuration.
     *
     * @return configuration
     */
    public JsonObject getConfiguration() {
        return configuration;
    }

    /**
     * Returns module's state, created in {@link Module#startup(StartupParameters)}
     *
     * @return
     */
    public JsonObject getState() {
        return state;
    }

    @Override
    public String toString() {
        return "ShutdownParameters{" +
                "configuration=" + configuration +
                ", state=" + state +
                '}';
    }

    /**
     * Used to build {@link ShutdownParameters} instances.
     */
    public static final class Builder {

        private JsonObject configuration;
        private JsonObject state;

        /**
         * Creates {@link Builder} instance.
         */
        public Builder() {

        }

        /**
         * Adds module's configuration.
         *
         * @param configuration module's configuration
         * @return this instance
         */
        public ShutdownParameters.Builder configuration(final JsonObject configuration) {
            this.configuration = configuration;

            return this;
        }

        /**
         * Adds module's state.
         *
         * @param state module's state
         * @return this instance
         */
        public ShutdownParameters.Builder state(final JsonObject state) {
            this.state = state;

            return this;
        }

        /**
         * Builds a {@link ShutdownParameters} instance.
         *
         * @return ShutdownParameters
         */
        public ShutdownParameters build() {
            if (this.configuration == null) {
                throw new IllegalStateException("Configuration may not be null");
            }

            if (this.state == null) {
                throw new IllegalStateException("State may not be null");
            }

            return new ShutdownParameters(configuration, state);
        }

    }
}
