package io.elastic.api;

/**
 * A component is an unit implementing a custom business logic to be executed
 * in the elastic.io runtime.
 *
 * <p>
 * A component is executed with a {@link ExecutionParameters} which provides
 * the component with a {@link Message}, configuration and a snapshot.
 * </p>
 *
 * <p>
 * The given {@link Message} represents component's input. Besides other things
 * it contains a payload to be consumed by a component.
 * </p>
 *
 * <p>
 * A configuration is an instance {@link com.google.gson.JsonObject} containing
 * required information, such as API key or username/password combination, that
 * components needs to collect from user to function properly.
 * </p>
 *
 * <p>
 * A snapshot is an instance of {@link com.google.gson.JsonObject} that represents
 * component's state. For example, a Twitter timeline component might store the id
 * of the last retrieved tweet for the next execution in order to ask Twitter for
 * tweets whose ids are greater than the one in the snapshot.
 * </p>
 *
 * A component communicates with elastic.io runtime by emitting events, such as
 * <i>data</i>, <i>error</i>, etc. For more info please check out {@link EventEmitter}.
 *
 * @see ExecutionParameters
 * @see Message
 * @see EventEmitter
 */
public abstract class Component {

    private EventEmitter eventEmitter;

    /**
     * Creates a component instance with the given {@link EventEmitter}.
     *
     * @param eventEmitter emitter to emit events
     */
    public Component(EventEmitter eventEmitter) {
        this.eventEmitter = eventEmitter;
    }

    /**
     * Executes this component with the given {@link ExecutionParameters}.
     *
     * @param parameters parameters to execute component with
     */
    abstract public void execute(ExecutionParameters parameters);

    /**
     * Used to emit data to component's callee by sending events.
     *
     * @return instance of EventEmitter
     */
    protected EventEmitter getEventEmitter() {
        return eventEmitter;
    }
}
