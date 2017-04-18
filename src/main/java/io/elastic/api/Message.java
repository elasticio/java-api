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
 *    JsonArray orders = message.getBody().getJsonArray("orders");
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

    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_BODY = "body";
    public static final String PROPERTY_HEADERS = "headers";
    public static final String PROPERTY_ATTACHMENTS = "attachments";
    public static final String PROPERTY_PASSTHROUGH = "passthrough";

    private UUID id;
    private JsonObject headers;
    private JsonObject body;
    private JsonObject attachments;
    private JsonObject passthrough;

    /**
     * Creates a message with headers, body and attachments.
     *
     * @param id          id of the message
     * @param headers     headers of the message
     * @param body        body of the message
     * @param attachments attachments of the message
     * @param passthrough passthrough of the message
     */
    private Message(final UUID id,
                    final JsonObject headers,
                    final JsonObject body,
                    final JsonObject attachments,
                    final JsonObject passthrough) {

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

        if (passthrough == null) {
            throw new IllegalArgumentException("Message passthrough must not be null");
        }

        this.id = id;
        this.headers = headers;
        this.body = body;
        this.attachments = attachments;
        this.passthrough = passthrough;
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

    /**
     * Returns message passthrough.
     *
     * @return passthrough
     */
    public JsonObject getPassthrough() {
        return passthrough;
    }

    /**
     * Returns this message as {@link JsonObject}.
     *
     * @return message as JSON object
     */
    public JsonObject toJsonObject() {
        return Json.createObjectBuilder()
                .add(PROPERTY_ID, id.toString())
                .add(PROPERTY_HEADERS, headers)
                .add(PROPERTY_BODY, body)
                .add(PROPERTY_ATTACHMENTS, attachments)
                .add(PROPERTY_PASSTHROUGH, passthrough)
                .build();
    }

    @Override
    public String toString() {
        final JsonObject json = toJsonObject();
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
        private JsonObject passthrough;

        /**
         * Default constructor.
         */
        public Builder() {
            this.id = UUID.randomUUID();
            this.headers = Json.createObjectBuilder().build();
            this.body = Json.createObjectBuilder().build();
            this.attachments = Json.createObjectBuilder().build();
            this.passthrough = Json.createObjectBuilder().build();
        }

        /**
         * Sets message id.
         *
         * @param id id for the message
         * @return same builder instance
         */
        public Builder id(final UUID id) {

            this.id = id;

            return this;
        }

        /**
         * Adds a headers to build message with.
         *
         * @param headers headers for the message
         * @return same builder instance
         */
        public Builder headers(final JsonObject headers) {

            this.headers = headers;

            return this;
        }

        /**
         * Adds a body to build message with.
         *
         * @param body body for the message
         * @return same builder instance
         */
        public Builder body(final JsonObject body) {

            this.body = body;

            return this;
        }

        /**
         * Adds attachments to build message with.
         *
         * @param attachments attachments for the message
         * @return same builder instance
         */
        public Builder attachments(final JsonObject attachments) {
            this.attachments = attachments;

            return this;
        }

        /**
         * Adds passthrough to build message with.
         *
         * @param passthrough passthrough for the message
         * @return same builder instance
         */
        public Builder passthrough(final JsonObject passthrough) {
            this.passthrough = passthrough;

            return this;
        }

        /**
         * Builds a {@link Message} instance and returns it.
         *
         * @return Message
         */
        public Message build() {
            return new Message(this.id, this.headers, this.body, this.attachments, this.passthrough);
        }
    }
}
