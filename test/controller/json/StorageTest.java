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

package controller.json;

import com.epictodo.controller.json.Storage;
import com.epictodo.model.Task;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class StorageTest {
    final String file_name = "storage_test";
    Task _task = new Task("Project Meeting", "2103 project meeting", 2);
    Task task_2 = new Task("Board Meeting", "Board of directors meeting", 2);
    private ArrayList<Task> task_list;
    private ArrayList<Task> expected_task;

    @Before
    public void initialize() throws IOException {
        task_list = new ArrayList<Task>();
        expected_task = Storage.loadDbFile(file_name);

        task_list.add(_task);
        task_list.add(task_2);

        Storage.saveToJson(file_name, task_list);
    }

    @Test
    public void assertSaveToJson() throws IOException {
        assertTrue(true);
    }

    @Test
    public void assertLoadDb() throws IOException {
        assertThat(task_list, is(expected_task));
    }
}
