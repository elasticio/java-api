package io.elastic.api

import com.google.gson.JsonObject
import spock.lang.Specification

class EventEmitterSpec extends Specification {

    def errorCallback = Mock(EventEmitter.Callback)
    def dataCallback = Mock(EventEmitter.Callback)
    def snapshotCallback = Mock(EventEmitter.Callback)

    def emitter
    def snapshot

    def setup() {
        emitter = new EventEmitter(errorCallback, dataCallback, snapshotCallback)
        snapshot = new JsonObject();
    }

    def "parse JSON object from #input results in #result"() {
        when:
        snapshot.addProperty("value", "I am snapshot")
        emitter.emitSnapshot(snapshot);

        then:
        1 * snapshotCallback.receive(snapshot)
        0 * dataCallback.receive(_)
        0 * errorCallback.receive(_)
    }
}