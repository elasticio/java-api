package io.elastic.api;

import java.io.Serializable;

/**
 * Message to be processed by {@link Executor}.
 */
public class Message implements Serializable {

    private String body;

    /**
     * Creates a message with a body.
     *
     * @param body body of the message
     */
    public Message(String body) {
        if (body == null) {
            throw new IllegalArgumentException("Message body is required");
        }

        this.body = body;
    }

    /**
     * Returns message body.
     *
     * @return body
     */
    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "{" +
                "body:'" + body + '\'' +
                '}';
    }
}
