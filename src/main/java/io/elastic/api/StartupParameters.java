package io.elastic.api;

import javax.json.JsonObject;
import java.io.Serializable;

/**
 * Represents parameters for a {@link Module#startup(StartupParameters)} method.
 */
public final class StartupParameters implements Serializable {

    private static final long serialVersionUID = 1L;

    private final JsonObject configuration;

    public StartupParameters(final JsonObject configuration) {
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
        return "StartupParameters{" +
                "configuration=" + configuration +
                '}';
    }

    /**
     * Used to build {@link StartupParameters} instances.
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
        public StartupParameters.Builder configuration(final JsonObject configuration) {
            this.configuration = configuration;

            return this;
        }

        /**
         * Builds a {@link StartupParameters} instance.
         *
         * @return ShutdownParameters
         */
        public StartupParameters build() {
            if (this.configuration == null) {
                throw new IllegalStateException("Configuration may not be null");
            }

            return new StartupParameters(configuration);
        }

    }
}
