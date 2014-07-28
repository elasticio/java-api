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
        System.err.println("new Executor()");
        this.componentClassName = componentClassName;
        this.eventEmitter = eventEmitter;
    }

    /**
     * Executes for given message.
     *
     * @param message message to process.
     */
    public void execute(Message message) {

        if (message == null) {
            eventEmitter.emitException(new IllegalArgumentException(
                    "Message is required. Please pass a message to Executor.execute(msg)"));

            return;
        }

        try {
            newComponent().process(message);
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
