package com.epictodo.model;

//@author A0111683L
public class FloatingTask extends Task {
    /************** Data members **********************/

    /**
     * *********** Constructors
     *
     * @throws Exception *********************
     */
    public FloatingTask(String taskName, String taskDescription, int priority) {
        super(taskName, taskDescription, priority);
    }

    public FloatingTask(Task t) {
        super(t.getTaskName(), t.getTaskDescription(), t.getPriority());
    }

    /**************** Accessors ***********************/

    /**************** Mutators ************************/

    /**
     * ************* Class Methods ***********************
     */
    /*
     * public FloatingTask clone() { String taskName = super.getTaskName();
	 * String taskDescription = super.getTaskDescription(); int priority =
	 * super.getPriority(); FloatingTask newClone = new FloatingTask(taskName,
	 * taskDescription, priority); return newClone; }
	 */
    public String toString() {
        return super.toString();
    }

    public String getDetail() {
        return super.getDetail();
    }

    public Boolean equals(FloatingTask task2) {
        Boolean compareTask = super.equals(task2);

        if (compareTask) {
            return true;
        } else {
            return false;
        }
    }

    public FloatingTask copy() {
        Task t = super.copy();
        FloatingTask cloned = null;

        cloned = new FloatingTask(t);
        cloned.setUid(t.getUid());
        return cloned;
    }
}
