package io.elastic.api;

import javax.json.JsonObject;
import java.io.Serializable;

/**
 * Represents parameters for a {@link Module#init(InitParameters)} method.
 */
public final class InitParameters implements Serializable {

    private static final long serialVersionUID = 1L;

    private final JsonObject configuration;

    public InitParameters(final JsonObject configuration) {
        this.configuration = configuration;
    }

    /**
     * Returns module's configuration.
     *
     * @return configuration
     */
    public JsonObject getConfiguration() {
        return configuration;
    }

    @Override
    public String toString() {
        return "InitParameters{" +
                "configuration=" + configuration +
                '}';
    }

    /**
     * Used to build {@link InitParameters} instances.
     */
    public static final class Builder {

        private JsonObject configuration;

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
        public InitParameters.Builder configuration(final JsonObject configuration) {
            this.configuration = configuration;

            return this;
        }

        /**
         * Builds a {@link InitParameters} instance.
         *
         * @return ShutdownParameters
         */
        public InitParameters build() {
            if (this.configuration == null) {
                throw new IllegalStateException("Configuration may not be null");
            }

            return new InitParameters(configuration);
        }

    }
}
