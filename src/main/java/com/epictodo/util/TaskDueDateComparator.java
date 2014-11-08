// @author A0112725N

package com.epictodo.util;

import java.util.Comparator;

import com.epictodo.model.DeadlineTask;
import com.epictodo.model.Task;
import com.epictodo.model.TimedTask;

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
			dt1 = ((TimedTask) o1).getEndDateTime();
		}
		if (o2 instanceof DeadlineTask) {
			dt2 = ((DeadlineTask) o2).getEndDateTime();
		} else if (o1 instanceof TimedTask) {
			dt2 = ((TimedTask) o2).getEndDateTime();
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
