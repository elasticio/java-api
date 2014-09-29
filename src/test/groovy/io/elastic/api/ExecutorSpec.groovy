package io.elastic.api

import com.google.gson.JsonObject
import io.elastic.api.demo.EchoComponent
import spock.lang.Specification

class ExecutorSpec extends Specification {

    def errorCallback = Mock(EventEmitter.Callback)
    def dataCallback = Mock(EventEmitter.Callback)
    def snapshotCallback = Mock(EventEmitter.Callback)
    def emitter = new EventEmitter(errorCallback, dataCallback, snapshotCallback)

    def executor
    def params


    def setup() {
        executor = new Executor(EchoComponent.class.getName(), emitter)

        def body = new JsonObject()
        body.addProperty('content', 'Hello, world!');

        def config = new JsonObject()
        config.addProperty('apiKey', 'secret');

        def snapshot = new JsonObject()
        snapshot.addProperty('timestamp', 12345);

        def msg = new Message.Builder().body(body).build()

        params =  new ExecutionParameters.Builder(msg)
                .configuration(config)
                .snapshot(snapshot)
                .build()
    }

    def "execute without parameters"() {
        when:
        executor.execute();

        then:
        0 * snapshotCallback.receive(_)
        0 * dataCallback.receive(_)
        1 * errorCallback.receive({
            assert it instanceof IllegalArgumentException
            assert it.message == 'ExecutionParameters is required. Please pass a parameters object to Executor.execute(parameters)'
            it
        })
    }

    def "execute component successfuly"() {
        when:
        executor.execute(params);

        then:
        1 * snapshotCallback.receive({it.toString() == '{"echo":{"timestamp":12345}}'})
        1 * dataCallback.receive({it.toString() == '{"body":{"echo":{"content":"Hello, world!"},"config":{"apiKey":"secret"}},"attachments":{}}'})
        0 * errorCallback.receive(_)
    }
}
