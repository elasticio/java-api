package io.elastic.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Message to be processed by {@link Executor}. A message may have a body,
 * which represents a message's payload to be processed, and multiple attachments.
 * Both body and attachments are {@link JsonObject}s.
 *
 * <p>
 *
 * A message is build using {@link Builder}, as shown in the following example.
 *
 * <pre>
 * {@code
 *    JsonElement orders = new Gson().toJsonTree(response.getOrders());
 *
 *    JsonObject body = new JsonObject();
 *    body.add("orders", orders);
 *
 *    Message message = new Message.Builder().body(body).build();
 * }
 * </pre>
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

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
        public Builder body(JsonObject body) {

            this.body = body;

            return this;
        }

        /**
         * Adds attachments to build message with.
         *
         * @param attachments attachments for the message
         * @return same builder instance
         */
        public Builder attachments(JsonObject attachments) {
            this.attachments = attachments;

            return this;
        }

        /**
         * Builds a {@link Message} instance and returns it.
         *
         * @return Message
         */
        public Message build() {
            return new Message(this.body, this.attachments);
        }
    }
}
