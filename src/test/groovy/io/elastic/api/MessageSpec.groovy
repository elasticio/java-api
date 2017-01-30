package io.elastic.api

import spock.lang.Specification

import javax.json.Json

class MessageSpec extends Specification {

    def "throw exception if headers null"() {
        when:
        new Message.Builder().headers(null).build()

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Message headers must not be null"
    }

    def "throw exception if body null"() {
        when:
        new Message.Builder().body(null).build()

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Message body must not be null"
    }

    def "throw exception if attachments null"() {
        when:
        new Message.Builder().attachments(null).build()

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Message attachments must not be null"
    }

    def "build message successfully"() {
        setup:
        def body = Json.createObjectBuilder()
                .add("message", "hello world!")
                .build()
        def headers = Json.createObjectBuilder()
                .add("x-io-rate-limit", 60)
                .build()
        def attachments = Json.createObjectBuilder()
                .add("logo.png", "http://acmer.org/img/logo.png")
                .build()

        def msg = new Message.Builder()
                .headers(headers)
                .body(body)
                .attachments(attachments)
                .build()

        expect:
        msg.getId() != null
        msg.getBody().toString() == '{"message":"hello world!"}'
        msg.getHeaders().toString() == '{"x-io-rate-limit":60}'
        msg.getAttachments().toString() == '{"logo.png":"http://acmer.org/img/logo.png"}'
    }
}
