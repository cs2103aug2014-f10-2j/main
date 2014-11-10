// @author A0112725N

package com.epictodo.util;

import com.epictodo.model.task.DeadlineTask;
import com.epictodo.model.task.Task;
import com.epictodo.model.task.TimedTask;

import java.util.Comparator;

/**
 * This class is a comparator for comparing task object by due date
 */
public class TaskDueDateComparator implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        long dt1 = 0, dt2 = 0;

        if (o1 instanceof DeadlineTask) {
            dt1 = ((DeadlineTask) o1).getEndDateTime();
        } else if (o1 instanceof TimedTask) {
            dt1 = ((TimedTask) o1).getStartDateTime();
        }
        if (o2 instanceof DeadlineTask) {
            dt2 = ((DeadlineTask) o2).getEndDateTime();
        } else if (o1 instanceof TimedTask) {
            dt2 = ((TimedTask) o2).getStartDateTime();
        }

        if (dt1 > dt2) {
            return 1;
        } else if (dt1 < dt2) {
            return -1;
        } else {
            return 0;
        }
    }
}
