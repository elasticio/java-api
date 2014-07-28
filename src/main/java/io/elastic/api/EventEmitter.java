package io.elastic.api;

public final class EventEmitter {

    private Callback errorCallback;
    private Callback dataCallback;
    private Callback snapshotCallback;

    public EventEmitter(Callback errorCallback, Callback dataCallback, Callback snapshotCallback) {
        this.errorCallback = errorCallback;
        this.dataCallback = dataCallback;
        this.snapshotCallback = snapshotCallback;
    }

    public EventEmitter emitException(Exception e) {

        return emit(errorCallback, e);
    }

    public EventEmitter emitData(Message message) {

        return emit(dataCallback, message);
    }

    public EventEmitter emitSnapshot(Object snapshot) {

        return emit(snapshotCallback, snapshot);
    }

    private EventEmitter emit(Callback callback, Object value) {
        callback.receive(value.toString());

        return this;
    }

}
