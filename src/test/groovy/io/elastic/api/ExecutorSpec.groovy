package io.elastic.api

import io.elastic.api.demo.EchoComponent
import io.elastic.api.demo.ErroneousComponent
import spock.lang.Specification

import javax.json.Json

class ExecutorSpec extends Specification {

    def errorCallback = Mock(EventEmitter.Callback)
    def dataCallback = Mock(EventEmitter.Callback)
    def snapshotCallback = Mock(EventEmitter.Callback)
    def reboundCallback = Mock(EventEmitter.Callback)
    def updateAccessTokenCallback = Mock(EventEmitter.Callback)
    def httpReplyCallback = Mock(EventEmitter.Callback)

    def emitter = new EventEmitter(
            errorCallback,
            dataCallback,
            snapshotCallback,
            reboundCallback,
            updateAccessTokenCallback,
            httpReplyCallback)

    def params


    def setup() {

        def body = Json.createObjectBuilder()
                .add('content', 'Hello, world!')
                .build()

        def config = Json.createObjectBuilder()
                .add('apiKey', 'secret')
                .build()

        def snapshot = Json.createObjectBuilder()
                .add('timestamp', 12345)
                .build()

        def msg = new Message.Builder().body(body).build()

        params = new ExecutionParameters.Builder(msg, emitter)
                .configuration(config)
                .snapshot(snapshot)
                .build()
    }

    def "execute without parameters"() {
        when:
        new Executor(EchoComponent.class.getName()).execute()

        then:
        IllegalArgumentException e = thrown()
        e.message == 'ExecutionParameters is required. Please pass a parameters object to Executor.execute(parameters)'
        0 * snapshotCallback.receive(_)
        0 * dataCallback.receive(_)
        0 * errorCallback.receive(_)
    }

    def "executing component failed"() {
        when:
        new Executor(ErroneousComponent.class.getName()).execute(params)

        then:
        0 * snapshotCallback.receive(_)
        0 * dataCallback.receive(_)
        1 * errorCallback.receive({
            assert it instanceof RuntimeException
            assert it.message == 'Ouch! We did not expect that'
            it
        })
    }

    def "execute component successfully"() {
        when:
        new Executor(EchoComponent.class.getName()).execute(params);

        then:
        1 * snapshotCallback.receive({ it.toString() == '{"echo":{"timestamp":12345}}' })
        1 * dataCallback.receive({
            JSON.stringify(it.getBody()) == "{\"echo\":{\"content\":\"Hello, world!\"},\"config\":{\"apiKey\":\"secret\"}}"
            it.getId() != null
            it.getHeaders().isEmpty()
            it.getAttachments().isEmpty()
        })
        0 * errorCallback.receive(_)
    }
}
