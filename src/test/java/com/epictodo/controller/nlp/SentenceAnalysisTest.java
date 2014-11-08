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

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Map;

public class SentenceAnalysisTest {
    private SentenceAnalysis sentence_analysis = new SentenceAnalysis();

    @Test
    public void DateAnalysisTest() throws ParseException {
        Map<String, String> date_time_map = sentence_analysis.dateTimeAnalyzer("project meeting next monday");

        for (Map.Entry<String, String> map_result : date_time_map.entrySet()) {
            String _key = map_result.getKey();

            Assert.assertEquals("next monday", _key);
        }
    }

    @Test
    public void DateAnalysisTest2() throws ParseException {
        Map<String, String> date_time_map = sentence_analysis.dateTimeAnalyzer("project meeting Tuesday 2 weeks later at 15:00");

        for (Map.Entry<String, String> map_result : date_time_map.entrySet()) {
            String _key = map_result.getKey();

            Assert.assertEquals("Tuesday 2 weeks later at 15:00", _key);
        }
    }

    @Test
    public void SentenceAnalysisTest() throws ParseException {
        Map<String, String> sentence_map = sentence_analysis.sentenceAnalyzer("submit project report to Damith for cs2103 by Monday");

        Assert.assertEquals(-1348032563, sentence_map.hashCode());
    }
}
