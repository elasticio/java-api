package io.elastic.api;

import java.lang.reflect.Constructor;

/**
 * Executes a Java component and passes the execution results to its
 * callee using given {@link Callback}s.
 */
public final class Executor {

    private String componentClassName;
    private EventEmitter eventEmitter;

    public Executor(String componentClassName, EventEmitter eventEmitter) {
        this.componentClassName = componentClassName;
        this.eventEmitter = eventEmitter;
    }

    /**
     * Executes for given message.
     *
     * @param parameters parameters to execute a component with.
     */
    public void execute(ExecutionParameters parameters) {

        if (parameters == null) {
            eventEmitter.emitException(new IllegalArgumentException(
                    "ExecutionParameters is required. Please pass a parameters object to Executor.execute(parameters)"));

            return;
        }

        try {
            newComponent().process(parameters);
        } catch (Exception e) {

            eventEmitter.emitException(e);
        }

    }

    private Component newComponent() throws Exception {
        final Class<?> clazz = Class.forName(this.componentClassName);

        final Constructor<?> constructor = clazz.getDeclaredConstructor(EventEmitter.class);

        return (Component) clazz.cast(constructor.newInstance(this.eventEmitter));
    }
}
