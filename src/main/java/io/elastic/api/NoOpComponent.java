package io.elastic.api;

public class NoOpComponent extends Component {

    public NoOpComponent(EventEmitter eventEmitter) {
        super(eventEmitter);

        System.err.println("Created new " + getClass().getName());
    }

    @Override
    protected void process(Message message) {

        getEventEmitter().emitData(new Message("it works!!!"));

    }
}
