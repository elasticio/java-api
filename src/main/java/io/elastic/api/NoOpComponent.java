package io.elastic.api;

import com.google.gson.JsonObject;

public class NoOpComponent extends Component {

    public NoOpComponent(EventEmitter eventEmitter) {
        super(eventEmitter);

        System.err.println("Created new " + getClass().getName());
    }

    @Override
    protected void execute(ExecutionParameters parameters) {

        System.err.println("Processing message: " + parameters);

        final JsonObject snapshot = new JsonObject();
        snapshot.add("echo", parameters.getSnapshot());

        getEventEmitter()
                .emitSnapshot(snapshot)
                .emitData(echoMessage(parameters));
    }

    private Message echoMessage(ExecutionParameters parameters) {

        final Message msg = parameters.getMessage();

        final JsonObject body = new JsonObject();
        body.add("echo", msg.getBody());
        body.add("config", parameters.getConfiguration());

        return new Message.Builder()
                .body(body)
                .attachments(msg.getAttachments())
                .build();
    }
}
