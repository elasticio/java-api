package io.elastic.api;

import com.google.gson.JsonObject;

/**
 * Used by a {@link Component} to communicate with the elastic.io runtime.
 *
 * The implementation of this class has been inspired by the
 * <a href="http://nodejs.org/api/events.html" target="_blank">Node.js EventEmitter</a> class.
 *
 * @see Component
 */
public final class EventEmitter {

    private Callback errorCallback;
    private Callback dataCallback;
    private Callback snapshotCallback;

    public EventEmitter(Callback errorCallback, Callback dataCallback, Callback snapshotCallback) {
        this.errorCallback = errorCallback;
        this.dataCallback = dataCallback;
        this.snapshotCallback = snapshotCallback;
    }

    /**
     * Emits an {@link Exception}.
     *
     * @param e exception to emit
     * @return this instance
     */
    public EventEmitter emitException(Exception e) {

        return emit(errorCallback, e);
    }

    /**
     * Emits a {@link Message}.
     *
     * @param message message to emit
     * @return this instance
     */
    public EventEmitter emitData(Message message) {

        return emit(dataCallback, message);
    }

    /**
     * Emits {@link JsonObject} snapshot.
     * @param snapshot snapshot to emit
     * @return this instance
     */
    public EventEmitter emitSnapshot(JsonObject snapshot) {

        return emit(snapshotCallback, snapshot);
    }

    private EventEmitter emit(Callback callback, Object value) {
        callback.receive(value);

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
