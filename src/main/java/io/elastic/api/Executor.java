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

    /**
     * Creates a new instance of {@link Executor} with given {@link Component} class name.
     *
     * @param componentClassName fully qualified name of a component to execute
     */
    public Executor(String componentClassName) {
        this.componentClassName = componentClassName;
    }

    /**
     * Executes a component with given {@link ExecutionParameters}.
     *
     * @param parameters parameters to execute a component with.
     */
    public void execute(ExecutionParameters parameters) {

        if (parameters == null) {
            throw new IllegalArgumentException(
                    "ExecutionParameters is required. Please pass a parameters object to Executor.execute(parameters)");
        }

        Component component;
        try {
            component = newComponent();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            logger.error("Component instantiation failed", e);
            parameters.getEventEmitter().emitException(e);
            return;
        }

        try {
            component.execute(parameters);
        } catch (RuntimeException e) {
            logger.error("Component instantiation failed", e);
            parameters.getEventEmitter().emitException(e);
        }

    }

    private Component newComponent() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.info("Instantiating component {}", componentClassName);

        final Class<?> clazz = Class.forName(this.componentClassName);

        return (Component) clazz.cast(clazz.newInstance());

    }
}
