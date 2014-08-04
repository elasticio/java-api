package io.elastic.api;

import java.lang.reflect.Constructor;

/**
 * Executes a {@link Component} and emits the execution results to its
 * callee using given {@link EventEmitter}.
 */
public final class Executor {

    private String componentClassName;
    private EventEmitter eventEmitter;

    /**
     * Creates a new instance of {@link Executor} with given {@link Component}
     * class name and {@link EventEmitter}.
     *
     * @param componentClassName fully qualified name of a component to execute
     * @param eventEmitter       used to emit execution results
     */
    public Executor(String componentClassName, EventEmitter eventEmitter) {
        this.componentClassName = componentClassName;
        this.eventEmitter = eventEmitter;
    }

    /**
     * Executes a component with given {@link ExecutionParameters}.
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
            newComponent().execute(parameters);
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
