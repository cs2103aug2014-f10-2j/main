/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Kenneth Ham
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.epictodo.logic;

import com.epictodo.model.InvalidDateException;
import com.epictodo.model.InvalidTimeException;
import com.epictodo.model.Task;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CRUDLogicTest {
	private CRUDLogic _logic = new CRUDLogic();
	private Task _nulltask = null;
	private Task _task1 = null;
	private Task _task2 = null;
	private ArrayList<Task> _tasklist;

	@Before
	public void ini() throws Exception {
		_nulltask = new Task(null, null, 0);
		_task1 = new Task("Project Meeting", "2103 project meeting", 2);
		_task2 = new Task("Board Meeting", "Board of directors meeting", 2);

	}

	@Test
	public void createEmptyTaskTest() {
		int count = _logic.size();
		_logic.createTask(_nulltask);
		assertEquals(count + 1, _logic.size());
	}

	@Test
	public void createNormalTaskTest() {
		int count = _logic.size();
		_logic.createTask(_task1);
		assertEquals(count + 1, _logic.size());
	}

	@Test
	public void removeNormalTaskTest() {
		_logic.createTask(_task1);
		int count = _logic.size();
		_logic.deleteTask(_task1);
		assertEquals(count - 1, _logic.size());
	}

	@Test
	public void displayTaskTest() {
		_logic.createTask(_task1);
		_logic.createTask(_task2);
		String result = _logic.displayAllTaskList();
		assertEquals("1. Project Meeting\r\n" + "2. Board Meeting\r\n", result);
	}

	@Test
	public void searchByTaskNameTest() throws ParseException,
			InvalidDateException, InvalidTimeException {
		_logic.createTask(_task1);
		_tasklist = new ArrayList<Task>();
		_tasklist.add(_task1);
		ArrayList<Task> result = _logic.getTasksByName("Meeting");
		assertEquals(_tasklist.get(0).getTaskName(), result.get(0)
				.getTaskName());
	}

	@Test
	public void undoAddOneTest() {
		_logic.createTask(_task1);
		_logic.undoMostRecent();
		assertEquals(_logic.size(), 0);
	}

	@Test
	public void undoLimitTest() {
		_logic.createTask(_task1);
		_logic.undoMostRecent();
		String result = _logic.undoMostRecent();
		assertEquals(result, CRUDLogic.MSG_NO_MORE_ACTIONS_TO_BE_UNDONE);
	}

	@Test
	public void redoTest() throws ParseException, InvalidDateException,
			InvalidTimeException {
		_logic.createTask(_task1);
		_logic.createTask(_task2);
		Task check = _logic.getAllTasks().get(1);
		_logic.undoMostRecent();
		_logic.redoMostRecent();
		assertEquals(check.getUid(), _logic.getAllTasks().get(1).getUid());
	}
	
	@Test
	public void redoLimitTest() {
		_logic.createTask(_task1);
		_logic.undoMostRecent();
		_logic.redoMostRecent();
		String result = _logic.redoMostRecent();
		assertEquals(result, CRUDLogic.MSG_NO_MORE_ACTIONS_TO_BE_REDONE);
	}
}
