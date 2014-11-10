//@author A0112725N

/**
 * This interface specifies any undoable actions
 */
package com.epictodo.model.command;

public interface Undoable {
    /**
     * This method enforces the ability to undo an action
     *
     * @return the result
     */
    public String undo();

    /**
     * This method enforces the ability to redo an action
     *
     * @return the result
     */
    public String redo();
}
