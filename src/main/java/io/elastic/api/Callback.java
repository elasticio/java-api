package io.elastic.api;

/**
 * This interface defines a callback to be used by {@link Executor}
 * to pass errors, data and snapshots to its callee.
 *
 */
public interface Callback {

    /**
     * Invoked by {@link Executor} to pass errors, data and snapshots asynchronously.
     *
     * @param data data to be passed
     */
    void receive(Object data);
}
