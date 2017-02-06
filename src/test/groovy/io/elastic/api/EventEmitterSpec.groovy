package io.elastic.api

import spock.lang.Specification

import javax.json.Json

class EventEmitterSpec extends Specification {

    def errorCallback = Mock(EventEmitter.Callback)
    def dataCallback = Mock(EventEmitter.Callback)
    def snapshotCallback = Mock(EventEmitter.Callback)
    def reboundCallback = Mock(EventEmitter.Callback)
    def updateKeysCallback = Mock(EventEmitter.Callback)
    def httpReplyCallback = Mock(EventEmitter.Callback)

    def emitter

    def setup() {
        emitter = new EventEmitter.Builder()
                .onError(errorCallback)
                .onData(dataCallback)
                .onSnapshot(snapshotCallback)
                .onRebound(reboundCallback)
                .onUpdateKeys(updateKeysCallback)
                .onHttpReplyCallback(httpReplyCallback)
                .build()
    }

    def "should emit data event"() {

        setup:
        def msg = new Message.Builder().build();

        when:
        emitter.emitData(msg);

        then:
        0 * snapshotCallback.receive(_)
        1 * dataCallback.receive(msg)
        0 * errorCallback.receive(_)
        0 * reboundCallback.receive(_)
        0 * updateKeysCallback.receive(_)
    }

    def "parse JSON object from #input results in #result"() {
        
        def snapshot = Json.createObjectBuilder().add("value", "I am snapshot").build()
         
        when:
        emitter.emitSnapshot(snapshot);

        then:
        1 * snapshotCallback.receive(snapshot)
        0 * dataCallback.receive(_)
        0 * errorCallback.receive(_)
        0 * reboundCallback.receive(_)
        0 * updateKeysCallback.receive(_)
    }
    
    def "should emit rebound event" () {

        def reboundCause = "Rebound me!"
        
        when:
        emitter.emitRebound("Rebound me!");

        then:
        0 * snapshotCallback.receive(_)
        0 * dataCallback.receive(_)
        0 * errorCallback.receive(_)
        1 * reboundCallback.receive(reboundCause)
        0 * updateKeysCallback.receive(_)
    }

    def "should emit error event" () {

        setup:
        def e = new RuntimeException("Ouch!")

        when:
        emitter.emitException(e);

        then:
        0 * snapshotCallback.receive(_)
        0 * dataCallback.receive(_)
        1 * errorCallback.receive(e)
        0 * reboundCallback.receive(_)
        0 * updateKeysCallback.receive(_)
    }

    def "should emit updateAccessToken event" () {
        setup:
        def obj = Json.createObjectBuilder()
                .add("access_token", "foo_bar")
                .add("refresh_token", "baz_barney")
                .add("expires_in", "3600")
                .build()

        when:
        emitter.emitUpdateKeys(obj);

        then:
        0 * snapshotCallback.receive(_)
        0 * dataCallback.receive(_)
        0 * errorCallback.receive(_)
        0 * reboundCallback.receive(_)
        1 * updateKeysCallback.receive(obj)
    }

    def "should emit httpReply event" () {
        setup:
        def reply = new HttpReply.Builder()
                .content(new ByteArrayInputStream("hello".getBytes()))
                .status(HttpReply.Status.OK)
                .header('X-Powered-By', 'elastic.io')
                .build()

        when:
        emitter.emitHttpReply(reply);

        then:
        0 * snapshotCallback.receive(_)
        0 * dataCallback.receive(_)
        0 * errorCallback.receive(_)
        0 * reboundCallback.receive(_)
        0 * updateKeysCallback.receive(_)
        1 * httpReplyCallback.receive(reply)
    }
}