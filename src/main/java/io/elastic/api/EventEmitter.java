package io.elastic.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.JsonObject;

/**
 * Used by a {@link Component} to communicate with the elastic.io runtime.
 * 
 * The implementation of this class has been inspired by the <a
 * href="http://nodejs.org/api/events.html" target="_blank">Node.js
 * EventEmitter</a> class.
 * 
 * @see Component
 */
public final class EventEmitter {
    private static final Logger logger = LoggerFactory.getLogger(EventEmitter.class);

    private Callback errorCallback;
    private Callback dataCallback;
    private Callback snapshotCallback;
    private Callback reboundCallback;
    private Callback updateKeysCallback;
    private Callback httpReplyCallback;

    private EventEmitter(Callback errorCallback,
                         Callback dataCallback,
                         Callback snapshotCallback,
                         Callback reboundCallback,
                         Callback updateKeysCallback,
                         Callback httpReplyCallback) {
        this.errorCallback = errorCallback;
        this.dataCallback = dataCallback;
        this.snapshotCallback = snapshotCallback;
        this.reboundCallback = reboundCallback;
        this.updateKeysCallback = updateKeysCallback;
        this.httpReplyCallback = httpReplyCallback;
    }

    /**
     * Emits an {@link Exception}. Used to emit an exception from component
     * written in asynchronous style, for example using
     * <a href="https://github.com/ReactiveX/RxJava" target="_blank">Reactive Extensions for the JVM </a>.
     * Any non-asynchronous may throw an exception instead of using this method.
     * 
     * @param e
     *            exception to emit
     * @return this instance
     */
    public EventEmitter emitException(Exception e) {

        return emit(errorCallback, e);
    }

    /**
     * Emits a {@link Message}.
     * 
     * @param message
     *            message to emit
     * @return this instance
     */
    public EventEmitter emitData(Message message) {

        return emit(dataCallback, message);
    }

    /**
     * Emits {@link JsonObject} snapshot.
     * 
     * @param snapshot
     *            snapshot to emit
     * @return this instance
     */
    public EventEmitter emitSnapshot(JsonObject snapshot) {

        return emit(snapshotCallback, snapshot);
    }
    
    /**
     * Emits the rebound event specifying a {@link Object} reason.
     * 
     * @param reason
     *            reason for rebound
     * @return this instance
     */
    public EventEmitter emitRebound(Object reason) {
        
        return emit(reboundCallback, reason);
    }

    /**
     * Emits the updateKeys event. This method is typically used in components authorizing with OAuth2 apis.
     * If an access token is expired, the component needs to refresh them. The refreshed tokens need to be communicated
     * to the elastic.io platform so that the component is executed with refreshed tokens next time.
     *
     * @param object
     *            object containing the tokens
     * @return this instance
     */
    public EventEmitter emitUpdateKeys(JsonObject object) {

        return emitOptional(updateKeysCallback, "updateKeys", object);
    }

    /**
     * Emits the httpReply event. This method is typically used to emit a HTTP reply in real-time flows.
     *
     * @param reply
     *            HTTP reply
     * @return this instance
     */
    public EventEmitter emitHttpReply(final HttpReply reply) {

        return emitOptional(httpReplyCallback, "httpReply", reply);
    }

    private EventEmitter emit(Callback callback, Object value) {
        callback.receive(value);

        return this;
    }

    private EventEmitter emitOptional(Callback callback, String eventName, Object value) {

        if (callback == null) {
            logger.info("Event {} emitted but no callback is registered", eventName);
        } else {
            callback.receive(value);
        }

        return this;
    }

    /**
     * This interface defines a callback to be used by {@link Executor} to pass
     * errors, data and snapshots to its callee.
     */
    public interface Callback {

        /**
         * Invoked by {@link Executor} to pass errors, data and snapshots
         * asynchronously.
         * 
         * @param data
         *            data to be passed
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
        private Callback reboundCallback;
        private Callback updateKeysCallback;
        private Callback httpReplyCallback;

        public Builder() {

        }

        /**
         * FOR INTERNAL USE ONLY.
         * 
         * Adds 'error' {@link Callback}.
         * 
         * @param callback
         *            callback invoked on error event
         * @return this instance
         */
        public Builder onError(Callback callback) {
            this.errorCallback = callback;

            return this;
        }

        /**
         * Adds 'data' {@link Callback}.
         * 
         * @param callback
         *            callback invoked on data event
         * @return this instance
         */
        public Builder onData(Callback callback) {
            this.dataCallback = callback;

            return this;
        }

        /**
         * Adds 'snapshot' {@link Callback}.
         * 
         * @param callback
         *            callback invoked on snapshot event
         * @return this instance
         */
        public Builder onSnapshot(Callback callback) {
            this.snapshotCallback = callback;

            return this;
        }

        /**
         * Adds 'rebound' {@link Callback}.
         * 
         * @param callback
         *            callback invoked on rebound event
         * @return this instance
         */
        public Builder onRebound(Callback callback) {
            this.reboundCallback = callback;
            
            return this;
        }

        /**
         * Adds 'updateAccessToken' {@link Callback}.
         *
         * @param callback
         *            callback invoked on updateAccessToken event
         * @return this instance
         */
        public Builder onUpdateKeys(Callback callback) {
            this.updateKeysCallback = callback;

            return this;
        }

        /**
         * Adds 'httpReply' {@link Callback}.
         *
         * @since 2.0
         *
         * @param callback
         *            callback invoked on httpReply event
         * @return this instance
         */
        public Builder onHttpReplyCallback(Callback callback) {
            this.httpReplyCallback = callback;

            return this;
        }
        
        /**
         * Builds an {@link EventEmitter} instance and returns it.
         * 
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

            if(this.reboundCallback == null) {
                throw new IllegalStateException("'onRebound' callback is required");
            }

            if(this.httpReplyCallback == null) {
                throw new IllegalStateException("'onHttpReplyCallback' callback is required");
            }
            
            return new EventEmitter(
                    errorCallback,
                    dataCallback,
                    snapshotCallback,
                    reboundCallback,
                    updateKeysCallback,
                    httpReplyCallback);
        }
    }

}
