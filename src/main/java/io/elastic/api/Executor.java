package io.elastic.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

/**
 * Executes a {@link Component} and emits the execution results to its
 * callee using given {@link EventEmitter}.
 */
public final class Executor {


    private static final Logger logger = LoggerFactory.getLogger(Executor.class);

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

            final IllegalArgumentException exception = new IllegalArgumentException(
                    "ExecutionParameters is required. Please pass a parameters object to Executor.execute(parameters)");

            logger.error(exception.getMessage());

            eventEmitter.emitException(exception);

            return;
        }

        try {
            newComponent().execute(parameters);
        } catch (Exception e) {
            logger.error("Component execution failed", e);

            eventEmitter.emitException(e);
        }

    }

    private Component newComponent() throws Exception {
        logger.debug("Instantiating component {}", componentClassName);

        final Class<?> clazz = Class.forName(this.componentClassName);

        final Constructor<?> constructor = clazz.getDeclaredConstructor(EventEmitter.class);

        return (Component) clazz.cast(constructor.newInstance(this.eventEmitter));
    }
}
