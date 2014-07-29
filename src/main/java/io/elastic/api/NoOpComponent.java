package io.elastic.api;

import com.google.gson.JsonObject;

public class NoOpComponent extends Component {

    public NoOpComponent(EventEmitter eventEmitter) {
        super(eventEmitter);

        System.err.println("Created new " + getClass().getName());
    }

    @Override
    protected void process(Message message) {

        System.err.println("Processing message: "+message);
        JsonObject body = new JsonObject();
        body.addProperty("message", "Hello, world!");

        getEventEmitter().emitData(new Message.MessageBuilder().body(body).build());

    }
}
