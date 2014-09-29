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

    private EventEmitter(Callback errorCallback, Callback dataCallback, Callback snapshotCallback) {
        this.errorCallback = errorCallback;
        this.dataCallback = dataCallback;
        this.snapshotCallback = snapshotCallback;
    }

    /**
     * @deprecated Please throw {@link Exception} instead.
     *
     * Emits an {@link Exception}.
     *
     * @param e exception to emit
     * @return this instance
     */
    EventEmitter emitException(Exception e) {

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


    /**
     * Used to build {@link EventEmitter} instances.
     */
    public static final class Builder {
        private Callback errorCallback;
        private Callback dataCallback;
        private Callback snapshotCallback;

        public Builder() {

        }

        /**
         * @deprecated Please throw {@link Exception} instead of emitting events.
         *
         * Adds 'error' {@link Callback}.
         * @param callback callback invoked on error event
         * @return this instance
         */
        public Builder onError(Callback callback) {
            this.errorCallback = callback;

            return this;
        }

        /**
         * Adds 'data' {@link Callback}.
         * @param callback callback invoked on data event
         * @return this instance
         */
        public Builder onData(Callback callback) {
            this.dataCallback = callback;

            return this;
        }

        /**
         * Adds 'snapshot' {@link Callback}.
         * @param callback callback invoked on snapshot event
         * @return this instance
         */
        public Builder onSnapshot(Callback callback) {
            this.snapshotCallback = callback;

            return this;
        }

        /**
         * Builds an {@link EventEmitter} instance and returns it.
         * @return EventEmitter
         */
        public EventEmitter build() {

            if (this.errorCallback == null) {
                throw new IllegalStateException("'onError' callback is required");
            }

            if (this.dataCallback == null) {
                throw new IllegalStateException("'onData' callback is required");
            }

            if (this.snapshotCallback == null) {
                throw new IllegalStateException("'onSnapshot' callback is required");
            }

            return new EventEmitter(errorCallback, dataCallback, snapshotCallback);
        }
    }

}
