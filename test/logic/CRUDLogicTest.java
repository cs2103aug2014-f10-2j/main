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

package logic;

import com.epictodo.logic.CRUDLogic;
import com.epictodo.model.Task;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CRUDLogicTest {
    private CRUDLogic crud_logic = new CRUDLogic();
    private Task task_null = new Task(null, null, 0);
    private Task _task = new Task("Project Meeting", "2103 project meeting", 2);
    private Task task_2 = new Task("Board Meeting", "Board of directors meeting", 2);
    private ArrayList<Task> task_list;

    @Test
    public void createEmptyTaskTest() {
        String _result = crud_logic.createTask(task_null);
        assertEquals("task added", _result);
    }

    @Test
    public void createNormalTaskTest() {
        String _result = crud_logic.createTask(_task);
        assertEquals("task added", _result);
    }

    @Test
    public void removeUnknownTaskTest() {
        String _result = crud_logic.deleteTask(task_2);
        assertEquals("can't remove task", _result);
    }

    @Test
    public void removeNormalTaskTest() {
        crud_logic.createTask(_task);
        String _result = crud_logic.deleteTask(_task);
        assertEquals("task removed", _result);
    }

    @Test
    public void displayTaskTest() {
        crud_logic.createTask(_task);
        crud_logic.createTask(task_2);
        String _result = crud_logic.displayAllTaskList();
        assertEquals("1. Project Meeting\r\n" +
                     "2. Board Meeting\r\n", _result);
    }
}
