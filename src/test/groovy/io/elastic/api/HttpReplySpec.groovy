package io.elastic.api

import spock.lang.Specification

class HttpReplySpec extends Specification {

    def "throw exception if conent null"() {
        when:
        new HttpReply.Builder().build()

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "HttpReply content must not be null"
    }

    def "build reply successfully"() {
        when:
        def stream = new ByteArrayInputStream("hello".getBytes())

        def reply = new HttpReply.Builder()
                .content(stream)
                .status(HttpReply.Status.ACCEPTED)
                .header('X-Powered-By', 'elastic.io')
                .build()

        then:
        reply.status == HttpReply.Status.ACCEPTED.statusCode
        reply.content == stream
        reply.headers == ['X-Powered-By': 'elastic.io']
    }
}
