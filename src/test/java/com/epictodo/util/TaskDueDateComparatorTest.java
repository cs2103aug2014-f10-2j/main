//@author A0112725N
package com.epictodo.util;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.epictodo.model.exception.InvalidDateException;
import com.epictodo.model.exception.InvalidTimeException;
import com.epictodo.model.task.DeadlineTask;
import com.epictodo.model.task.TimedTask;

public class TaskDueDateComparatorTest {
	private static final int COMP_RESULT_EQUAL = 0;
	private static final int COMP_RESULT_GREATER = 1;
	private static final int COMP_RESULT_SMALLER = -1;

	private static TaskDueDateComparator _comp;
	private static DeadlineTask _dt1, _dt1c, _dt2;
	private static TimedTask _tt1, _tt1c, _tt2;

	@BeforeClass
	public static void setUpBefore() throws Exception {
		_comp = new TaskDueDateComparator();
		_dt1 = new DeadlineTask("test name", "test desc", 2, "130615", "08:00");
		_dt1c = _dt1.copy();
		_dt2 = _dt1.copy();
		_dt2 = new DeadlineTask("test name", "test desc", 2, "130615", "16:00");
		_tt1 = new TimedTask("test name", "test desc", 2, "130615", "08:00", 2);
		_tt1c = _tt1.copy();
		_tt2 = new TimedTask("test name", "test desc", 2, "130615",
				"16:00", 2);
	}

	@Test
	public void testDeadlineTaskEqual() {
		int result = _comp.compare(_dt1, _dt1c);
		assertEquals(result, COMP_RESULT_EQUAL);
	}

	@Test
	public void testDeadlineTaskSmaller() {
		int result = _comp.compare(_dt1, _dt2);
		assertEquals(result, COMP_RESULT_SMALLER);
	}

	@Test
	public void testDeadlineTaskGreater() {
		int result = _comp.compare(_dt2, _dt1);
		assertEquals(result, COMP_RESULT_GREATER);
	}

	@Test
	public void testTimedTaskEqual() {
		int result = _comp.compare(_tt1, _tt1c);
		assertEquals(result, COMP_RESULT_EQUAL);
	}

	@Test
	public void testTimedTaskSmaller() {
		int result = _comp.compare(_tt1, _tt2);
		assertEquals(result, COMP_RESULT_SMALLER);
	}

	@Test
	public void testTimedTaskGreater() {
		int result = _comp.compare(_tt2, _tt1);
		assertEquals(result, COMP_RESULT_GREATER);
	}

	@Test
	public void testCrossTypeEqual() {
		int result = _comp.compare(_tt1, _dt1);
		assertEquals(result, COMP_RESULT_EQUAL);
	}

	@Test
	public void testCrossTypeSmaller() {
		int result = _comp.compare(_tt1, _dt2);
		assertEquals(result, COMP_RESULT_SMALLER);
	}

	@Test
	public void testCrossTypeGreater() {
		int result = _comp.compare(_dt2, _tt1);
		assertEquals(result, COMP_RESULT_GREATER);
	}
	
}
