package io.elastic.api

import com.google.gson.JsonObject
import spock.lang.Specification

class EventEmitterSpec extends Specification {

    def errorCallback = Mock(EventEmitter.Callback)
    def dataCallback = Mock(EventEmitter.Callback)
    def snapshotCallback = Mock(EventEmitter.Callback)
    def reboundCallback = Mock(EventEmitter.Callback)
    
    def emitter

    def setup() {
        emitter = new EventEmitter(errorCallback, dataCallback, snapshotCallback, reboundCallback)
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
    }
}