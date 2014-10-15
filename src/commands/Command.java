package commands;

/**
 * This Interface is part of the command pattern, which contains the execute
 * function.
 *
 * @author AHMED ALY
 * @version 13.10.2014
 */
public interface Command<T> {
    /**
     * This function purpose is to execute a function from another object.
     */
    T execute();
}