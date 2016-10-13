package io.elastic.api

import com.google.gson.JsonObject
import spock.lang.Specification

class EventEmitterSpec extends Specification {

    def errorCallback = Mock(EventEmitter.Callback)
    def dataCallback = Mock(EventEmitter.Callback)
    def snapshotCallback = Mock(EventEmitter.Callback)
    def reboundCallback = Mock(EventEmitter.Callback)
    def updateKeysCallback = Mock(EventEmitter.Callback)

    def emitter

    def setup() {
        emitter = new EventEmitter.Builder()
                .onError(errorCallback)
                .onData(dataCallback)
                .onSnapshot(snapshotCallback)
                .onRebound(reboundCallback)
                .onUpdateKeys(updateKeysCallback)
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
        
        def snapshot = new JsonObject()
         
        when:
        snapshot.addProperty("value", "I am snapshot")
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
        def obj = new JsonObject()
        obj.addProperty("access_token", "foo_bar")
        obj.addProperty("refresh_token", "baz_barney")
        obj.addProperty("expires_in", "3600")

        when:
        emitter.emitUpdateKeys(obj);

        then:
        0 * snapshotCallback.receive(_)
        0 * dataCallback.receive(_)
        0 * errorCallback.receive(_)
        0 * reboundCallback.receive(_)
        1 * updateKeysCallback.receive(obj)
    }
}