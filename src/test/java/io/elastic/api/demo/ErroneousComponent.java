package io.elastic.api.demo;

import io.elastic.api.Component;
import io.elastic.api.EventEmitter;
import io.elastic.api.ExecutionParameters;

public class ErroneousComponent implements Component {

    @Override
    public void execute(ExecutionParameters parameters) {

        throw new RuntimeException("Ouch! We did not expect that");
    }
}
