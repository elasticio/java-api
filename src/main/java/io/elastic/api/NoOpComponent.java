package io.elastic.api;

import com.google.gson.JsonObject;

public class NoOpComponent extends Component {

    public NoOpComponent(EventEmitter eventEmitter) {
        super(eventEmitter);

        System.err.println("Created new " + getClass().getName());
    }

    @Override
    protected void process(ExecutionParameters parameters) {

        System.err.println("Processing message: " + parameters.getMessage());
        JsonObject body = new JsonObject();
        body.addProperty("message", "Hello, world!");

        getEventEmitter().emitData(new Message.MessageBuilder().body(body).build());

    }
}
