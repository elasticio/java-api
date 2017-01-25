package io.elastic.api.demo;

import io.elastic.api.Component;
import io.elastic.api.EventEmitter;
import io.elastic.api.ExecutionParameters;
import io.elastic.api.Message;

import javax.json.Json;
import javax.json.JsonObject;

public class EchoComponent extends Component {

    public EchoComponent(EventEmitter eventEmitter) {
        super(eventEmitter);
    }

    @Override
    public void execute(ExecutionParameters parameters) {

        final JsonObject snapshot = Json.createObjectBuilder()
                .add("echo", parameters.getSnapshot())
                .build();

        getEventEmitter()
                .emitSnapshot(snapshot)
                .emitData(echoMessage(parameters));
    }

    private Message echoMessage(ExecutionParameters parameters) {

        final Message msg = parameters.getMessage();

        final JsonObject body = Json.createObjectBuilder()
                .add("echo", msg.getBody())
                .add("config", parameters.getConfiguration())
                .build();

        return new Message.Builder()
                .body(body)
                .attachments(msg.getAttachments())
                .build();
    }
}