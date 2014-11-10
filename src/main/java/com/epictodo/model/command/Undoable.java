// @author A0112725N

package com.epictodo.model.command;

public interface Undoable {
    public String undo();

    public String redo();
}
