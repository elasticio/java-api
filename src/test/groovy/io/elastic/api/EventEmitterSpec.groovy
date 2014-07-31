package io.elastic.api

import spock.lang.Specification

class EventEmitterSpec extends Specification {

    def errorCallback = Mock(EventEmitter.Callback)
    def dataCallback = Mock(EventEmitter.Callback)
    def snapshotCallback = Mock(EventEmitter.Callback)

    def emitter;

    def setup() {
        emitter = new EventEmitter(errorCallback, dataCallback, snapshotCallback)
    }

    def "parse JSON object from #input results in #result"() {
        when:
        emitter.emitSnapshot("DATA");

        then:
        1 * snapshotCallback.receive("DATA")
        0 * dataCallback.receive(_)
        0 * errorCallback.receive(_)
    }
}