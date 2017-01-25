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

    def emitter = new EventEmitter(
            errorCallback,
            dataCallback,
            snapshotCallback,
            reboundCallback,
            updateAccessTokenCallback)

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

        params = new ExecutionParameters.Builder(msg)
                .configuration(config)
                .snapshot(snapshot)
                .build()
    }

    def "execute without parameters"() {
        when:
        new Executor(EchoComponent.class.getName(), emitter).execute()

        then:
        0 * snapshotCallback.receive(_)
        0 * dataCallback.receive(_)
        1 * errorCallback.receive({
            assert it instanceof IllegalArgumentException
            assert it.message == 'ExecutionParameters is required. Please pass a parameters object to Executor.execute(parameters)'
            it
        })
    }

    def "executing component failed"() {
        when:
        new Executor(ErroneousComponent.class.getName(), emitter).execute(params)

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
        new Executor(EchoComponent.class.getName(), emitter).execute(params);

        then:
        1 * snapshotCallback.receive({ it.toString() == '{"echo":{"timestamp":12345}}' })
        1 * dataCallback.receive({ it.toString() == '{"body":{"echo":{"content":"Hello, world!"},"config":{"apiKey":"secret"}},"attachments":{}}' })
        0 * errorCallback.receive(_)
    }
}
