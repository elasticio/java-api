package io.elastic.api.demo;

import io.elastic.api.Module;
import io.elastic.api.ExecutionParameters;

public class ErroneousComponent implements Module {

    @Override
    public void execute(ExecutionParameters parameters) {

        throw new RuntimeException("Ouch! We did not expect that");
    }
}
