package io.elastic.api;

import com.google.gson.JsonObject;

public abstract class Component {

    private EventEmitter eventEmitter;

    public Component(EventEmitter eventEmitter) {
        this.eventEmitter = eventEmitter;
    }

    abstract protected void process(Message message, JsonObject config, JsonObject snapshot);

    protected EventEmitter getEventEmitter() {
        return eventEmitter;
    }
}
