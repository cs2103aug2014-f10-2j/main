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

package com.epictodo.controller.logic;

import com.epictodo.controller.logic.CRUDLogic;
import com.epictodo.model.exception.InvalidDateException;
import com.epictodo.model.exception.InvalidTimeException;
import com.epictodo.model.task.Task;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

//@author A0111683L
public class CRUDLogicTest {

	private static final String MSG_NO_MORE_ACTIONS_TO_BE_REDONE = "no more actions to be redone";
	private static final String MSG_NO_MORE_ACTIONS_TO_BE_UNDONE = "no more actions to be undone";

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

	//@author A0112725N
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
		assertEquals(result, MSG_NO_MORE_ACTIONS_TO_BE_UNDONE);
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
		assertEquals(result, MSG_NO_MORE_ACTIONS_TO_BE_REDONE);
	}

	//@author A0111683L
	@Test
	public void testUndoAdd1() throws NullPointerException, ParseException,
			InvalidDateException, InvalidTimeException {
		_logic.createTask(_task1);
		assertEquals("adding task \"Project Meeting\" is undone",
				_logic.undoMostRecent());
	}

	@Test
	public void testUndoAdd2() throws NullPointerException, ParseException,
			InvalidDateException, InvalidTimeException {
		_logic.createTask(_task2);
		assertEquals("adding task \"Board Meeting\" is undone",
				_logic.undoMostRecent());
	}

	@Test
	public void testUndoDelete1() throws NullPointerException, ParseException,
			InvalidDateException, InvalidTimeException {
		_logic.createTask(_task1);
		_logic.deleteTask(_task1);
		assertEquals("deleting task \"Project Meeting\" is undone",
				_logic.undoMostRecent());
	}

	@Test
	public void testUndoDelete2() throws NullPointerException, ParseException,
			InvalidDateException, InvalidTimeException {
		_logic.createTask(_task1);
		_logic.createTask(_task2);
		_logic.deleteTask(_task1);
		_logic.deleteTask(_task2);
		assertEquals("deleting task \"Board Meeting\" is undone",
				_logic.undoMostRecent());
	}

	@Test
	public void testMark1() {
		_logic.createTask(_task1);
		String _result = _logic.markAsDone(_task1);
		assertEquals("task \"Project Meeting\" is marked as done", _result);
	}

	@Test
	public void testMark2() {
		_logic.createTask(_task2);
		String _result = _logic.markAsDone(_task2);
		assertEquals("task \"Board Meeting\" is marked as done", _result);
	}

	@Test
	public void testUndoUpdate1() throws NullPointerException, ParseException,
			InvalidDateException, InvalidTimeException {
		_logic.createTask(_task1);
		_logic.updateTask(_task1, _task2);
		assertEquals("updating task \"Project Meeting\" is undone",
				_logic.undoMostRecent());
	}

	@Test
	public void testUndoUpdate2() throws NullPointerException, ParseException,
			InvalidDateException, InvalidTimeException {
		_logic.createTask(_task1);
		_logic.updateTask(_task1, _task2);
		assertEquals("updating task \"Project Meeting\" is undone",
				_logic.undoMostRecent());
	}

	@Test
	public void testRedoAdd1() throws NullPointerException, ParseException,
			InvalidDateException, InvalidTimeException {
		_logic.createTask(_task1);
		_logic.undoMostRecent();
		assertEquals("adding task \"Project Meeting\" is redone",
				_logic.redoMostRecent());
	}

	@Test
	public void testRedoAdd2() throws NullPointerException, ParseException,
			InvalidDateException, InvalidTimeException {
		_logic.createTask(_task2);
		_logic.undoMostRecent();
		assertEquals("adding task \"Board Meeting\" is redone",
				_logic.redoMostRecent());
	}

	@Test
	public void testRedoDelete1() throws NullPointerException, ParseException,
			InvalidDateException, InvalidTimeException {
		_logic.createTask(_task1);
		_logic.deleteTask(_task1);
		_logic.undoMostRecent();
		assertEquals("deleting task \"Project Meeting\" is redone",
				_logic.redoMostRecent());
	}

	@Test
	public void testRedoDelete2() throws NullPointerException, ParseException,
			InvalidDateException, InvalidTimeException {
		_logic.createTask(_task1);
		_logic.createTask(_task2);
		_logic.deleteTask(_task1);
		_logic.deleteTask(_task2);
		_logic.undoMostRecent();
		_logic.undoMostRecent();
		assertEquals("deleting task \"Project Meeting\" is redone",
				_logic.redoMostRecent());
	}

	@Test
	public void testRedoUpdate1() throws NullPointerException, ParseException,
			InvalidDateException, InvalidTimeException {
		_logic.createTask(_task1);
		_logic.updateTask(_task1, _task2);
		_logic.undoMostRecent();
		assertEquals("updating task \"Project Meeting\" is redone",
				_logic.redoMostRecent());
	}

	@Test
	public void testRedoUpdate2() throws NullPointerException, ParseException,
			InvalidDateException, InvalidTimeException {
		_logic.createTask(_task1);
		_logic.updateTask(_task1, _task2);
		_logic.undoMostRecent();
		_logic.undoMostRecent();
		assertEquals("adding task \"Project Meeting\" is redone",
				_logic.redoMostRecent());
	}
}
