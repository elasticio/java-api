package io.elastic.api;

import com.google.gson.JsonObject;

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

    public EventEmitter emitSnapshot(JsonObject snapshot) {

        return emit(snapshotCallback, snapshot);
    }

    private EventEmitter emit(Callback callback, Object value) {
        callback.receive(value.toString());

        return this;
    }

    /**
     * This interface defines a callback to be used by {@link Executor}
     * to pass errors, data and snapshots to its callee.
     */
    public interface Callback {

        /**
         * Invoked by {@link Executor} to pass errors, data and snapshots asynchronously.
         *
         * @param data data to be passed
         */
        void receive(Object data);
    }

}
