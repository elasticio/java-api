package io.elastic.api;

public class ErroneousComponent extends Component {

    public ErroneousComponent(EventEmitter eventEmitter) {
        super(eventEmitter);
    }

    @Override
    protected void execute(ExecutionParameters parameters) {

        getEventEmitter().emitException(new IllegalStateException("Ouch"));
    }
}
