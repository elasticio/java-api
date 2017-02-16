package io.elastic.api;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.UUID;

/**
 * Message to be processed by a {@link Module}. A message may have a body,
 * which represents a message's payload to be processed, and multiple attachments.
 * Both body and attachments are {@link JsonObject}s.
 *
 * <p>
 *
 * A {@link Module} may retrieve a value from {@link Message}'s body by a name,
 * as shown in the following example.
 *
 * <pre>
 * {@code
 *    JsonArray orders = message.getBody().orders("orders");
 * }
 * </pre>
 *
 * <p>
 *
 * A message is build using {@link Builder}, as shown in the following example.
 *
 * <pre>
 * {@code
 *    JsonArray orders = JSON.parseArray(response.getOrders());
 *
 *    JsonObject body = Json.createObjectBuilder()
 *            .add("orders", orders)
 *            .build();
 *
 *    Message message = new Message.Builder().body(body).build();
 * }
 * </pre>
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private JsonObject headers;
    private JsonObject body;
    private JsonObject attachments;

    /**
     * Creates a message with headers, body and attachments.
     *
     * @param id          id of the message
     * @param headers     headers of the message
     * @param body        body of the message
     * @param attachments attachments of the message
     */
    private Message(final UUID id,
                    final JsonObject headers,
                    final JsonObject body,
                    final JsonObject attachments) {

        if (id == null) {
            throw new IllegalArgumentException("Message id must not be null");
        }

        if (headers == null) {
            throw new IllegalArgumentException("Message headers must not be null");
        }

        if (body == null) {
            throw new IllegalArgumentException("Message body must not be null");
        }

        if (attachments == null) {
            throw new IllegalArgumentException("Message attachments must not be null");
        }

        this.id = id;
        this.headers = headers;
        this.body = body;
        this.attachments = attachments;
    }

    /**
     * Returns message id.
     *
     * @return id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns message headers.
     *
     * @return headers
     */
    public JsonObject getHeaders() {
        return headers;
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
        final JsonObject json = Json.createObjectBuilder()
                .add("id", id.toString())
                .add("headers", headers)
                .add("body", body)
                .add("attachments", attachments)
                .build();
        final StringWriter writer = new StringWriter();
        final JsonWriter jsonWriter = Json.createWriter(writer);
        jsonWriter.writeObject(json);
        jsonWriter.close();

        return writer.toString();
    }

    /**
     * Used to build {@link Message} instances.
     */
    public static final class Builder {
        private UUID id;
        private JsonObject headers;
        private JsonObject body;
        private JsonObject attachments;

        /**
         * Default constructor.
         */
        public Builder() {
            this.id = UUID.randomUUID();
            this.headers = Json.createObjectBuilder().build();
            this.body = Json.createObjectBuilder().build();
            this.attachments = Json.createObjectBuilder().build();
        }

        /**
         * Sets message id.
         *
         * @param id id for the message
         * @return same builder instance
         */
        public Builder id(UUID id) {

            this.id = id;

            return this;
        }

        /**
         * Adds a headers to build message with.
         *
         * @param headers headers for the message
         * @return same builder instance
         */
        public Builder headers(JsonObject headers) {

            this.headers = headers;

            return this;
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
            return new Message(this.id, this.headers, this.body, this.attachments);
        }
    }
}
