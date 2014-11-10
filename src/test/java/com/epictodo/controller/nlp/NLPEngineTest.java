//@author A0111875E
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

package com.epictodo.controller.nlp;

import com.epictodo.model.nlp.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NLPEngineTest {
    private final NLPEngine nlp_engine = NLPEngine.getInstance();

    @BeforeClass
    public static void initialize() {
    }

    /**
     * Test assumptions:
     * Date has to change if testing for date.
     * Date will always compare with Today's Date
     */

    @Test
    public void flexiAddTest() throws ParseException {
        String sentence = "project submission for cs2103 on monday by 23:59";
        Response expected = new Response();
        expected.setTaskTime("23:59");
        Response results = nlp_engine.flexiAdd(sentence);

        Assert.assertEquals(expected.getTaskTime(), results.getTaskTime());
    }

    @Test
    public void flexiAddTest2() throws ParseException {
        List<String> task_name = new ArrayList<>();
        task_name.add("submission");
        task_name.add("cs2103");
        task_name.add("project");
        task_name.add("monday");
        String sentence = "project submission for cs2103 on monday by 23:59";
        Response expected = new Response();
        expected.setTaskName(task_name);
        Response results = nlp_engine.flexiAdd(sentence);

        Assert.assertEquals(expected.getTaskName(), results.getTaskName());
    }
}
