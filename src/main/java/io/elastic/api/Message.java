package io.elastic.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Message to be processed by {@link Executor}.
 */
public class Message implements Serializable {

    private JsonObject body;
    private JsonObject attachments;

    /**
     * Creates a message with a body.
     *
     * @param body        body of the message
     * @param attachments attachments of the message
     */
    private Message(final JsonObject body, final JsonObject attachments) {
        if (body == null) {
            throw new IllegalArgumentException("Message body must not be null");
        }

        if (attachments == null) {
            throw new IllegalStateException("Attachments must not be null");
        }

        this.body = body;
        this.attachments = attachments;
    }

    /**
     * Returns message body.
     *
     * @return body
     */
    public JsonObject getBody() {
        return body;
    }

    /**
     * Returns message attachments.
     *
     * @return attachments
     */
    public JsonObject getAttachments() {
        return attachments;
    }

    @Override
    public String toString() {
        final Gson gson = new Gson();

        return gson.toJson(this);
    }

    /**
     * Used to build {@link Message} instances.
     */
    public static final class Builder {
        private JsonObject body;
        private JsonObject attachments;

        /**
         * Default constructor.
         */
        public Builder() {
            this.body = new JsonObject();
            this.attachments = new JsonObject();
        }

        /**
         * Adds a body to build message with.
         *
         * @param body body for the message
         * @return same builder instance
         */
        public Builder body(Object body) {

            this.body = parseJsonObject(body);

            return this;
        }

        /**
         * Adds attachments to build message with.
         *
         * @param attachments attachments for the message
         * @return same builder instance
         */
        public Builder attachments(Object attachments) {
            this.attachments = parseJsonObject(attachments);

            return this;
        }

        /**
         * Builds a {@link Message} instance and returns it.
         *
         * @return Message
         */
        public Message build() {
            if (this.body == null) {
                throw new IllegalStateException("Body is required");
            }

            if (this.attachments == null) {
                throw new IllegalStateException("Attachments is required");
            }

            return new Message(this.body, this.attachments);
        }

        private JsonObject parseJsonObject(Object body) {
            final Gson gson = new Gson();
            JsonElement json;

            if (body instanceof String) {
                json = gson.fromJson(body.toString(), (Type) JsonObject.class);
            } else {
                json = gson.toJsonTree(body);
            }

            if (!json.isJsonObject()) {
                throw new IllegalArgumentException(String.format("'%s' cannot be parsed to a JsonObject", body));
            }

            return (JsonObject) json;
        }

    }
}
