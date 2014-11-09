package com.epictodo.model;

//@author A0111683L
public abstract class Task {

    /**
     * *********** Data members *********************
     */
    long _uid;
    String _taskName;
    String _taskDescription;
    int _priority;
    boolean _isDone;

    /**
     * *********** Constructors
     *
     * @throws Exception *********************
     */

    public Task(String taskName, String taskDescription, int priority) {
        /*
		 * New member: long uid
		 */
        _uid = -1; // -1 indicates an newly created Task object

        setTaskName(taskName);
        setTaskDescription(taskDescription);
        setPriority(priority);
        setIsDone(false);
    }

    /**
     * ************* Accessors **********************
     */
    public long getUid() {
        return this._uid;
    }

    /**
     * ************* Mutators ***********************
     */
    public void setUid(long uid) {
        this._uid = uid;
    }

    public String getTaskName() {
        return _taskName;
    }

    public void setTaskName(String newTask) {
        _taskName = newTask;
    }

    public String getTaskDescription() {
        return _taskDescription;
    }

    public void setTaskDescription(String newTaskDescription) {
        _taskDescription = newTaskDescription;
    }

    public int getPriority() {
        return _priority;
    }

    public void setPriority(int newPriority) {
        assert newPriority >= 0 && newPriority <= 10;
        _priority = newPriority;
    }

    public boolean getIsDone() {
        return _isDone;
    }

    public void setIsDone(boolean status) {
        assert status == true || status == false;
        _isDone = status;
    }

    /**
     * ************* Class methods ***********************
     */
    public String getDetail() {
        return "Name: " + this._taskName + '\n' + "Description: " + this._taskDescription + '\n';
    }

    public Boolean equals(Task task2) {
        Boolean uid = false;
        Boolean taskName = false;
        Boolean taskDescription = false;
        Boolean priority = false;
        Boolean isDone = false;

        if (this.getTaskName() == task2.getTaskName()) {
            taskName = true;
        }
        if (this.getUid() == task2.getUid()) {
            uid = true;
        }
        if (this.getTaskDescription() == task2.getTaskDescription()) {
            taskDescription = true;
        }
        if (this.getPriority() == task2.getPriority()) {
            priority = true;
        }
        if (this.getIsDone() == task2.getIsDone()) {
            isDone = true;
        }

        if (uid && taskName && taskDescription && priority && isDone) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return getTaskName();
    }

    public abstract Task copy () throws InvalidDateException, InvalidTimeException;
}