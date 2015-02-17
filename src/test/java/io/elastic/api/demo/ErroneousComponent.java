package io.elastic.api.demo;

import com.google.gson.JsonObject;

import io.elastic.api.Component;
import io.elastic.api.EventEmitter;
import io.elastic.api.ExecutionParameters;

public class ErroneousComponent extends Component {

    public ErroneousComponent(EventEmitter eventEmitter) {
        super(eventEmitter);
    }

    @Override
    public void execute(ExecutionParameters parameters) {

        throw new RuntimeException("Ouch! We did not expect that");
    }

	@Override
	public JsonObject getMetaModel(JsonObject configuration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject getSelectModel(JsonObject configuration, String identifier) {
		// TODO Auto-generated method stub
		return null;
	}
}
