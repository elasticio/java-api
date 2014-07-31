package io.elastic.api;

public abstract class Component {

    private EventEmitter eventEmitter;

    public Component(EventEmitter eventEmitter) {
        this.eventEmitter = eventEmitter;
    }

    abstract protected void execute(ExecutionParameters parameters);

    protected EventEmitter getEventEmitter() {
        return eventEmitter;
    }
}
