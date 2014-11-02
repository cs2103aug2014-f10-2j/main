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

package com.epictodo.engine;

import com.epictodo.controller.nlp.SentenceAnalysis;
import com.epictodo.util.TimeValidator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.*;

public class NLPEngine {
    protected StanfordCoreNLP _pipeline;
    private PrintStream _err = System.err;
    private NLPLoadEngine load_engine = new NLPLoadEngine();
    private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    public NLPEngine() {
        _pipeline = load_engine._pipeline;
    }

    /**
     * This method mutes NLP API Error Messages temporarily
     */
    public void mute() {
        System.setErr(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));
    }

    /**
     * This method restores NLP API Error Messages to be displayed
     */
    public void restore() {
        System.setErr(_err);
    }

    /**
     * This method understands the natural input from an input sentence
     * The sentence is analyzed and important information will be extracted and returned
     *
     * @param _sentence
     * @throws ParseException
     */
    public void flexiAdd(String _sentence) throws ParseException {
        SentenceAnalysis sentence_analysis = new SentenceAnalysis(_pipeline);
        Map<String, String> date_time_map = sentence_analysis.dateTimeAnalyzer(_sentence);
        Map<String, String> sentence_token_map = sentence_analysis.sentenceAnalyzer(_sentence);
        LinkedHashMap<String, LinkedHashSet<String>> entities_map = sentence_analysis.nerEntitiesExtractor(_sentence, load_engine.classifierModel());
        Map<String, String> analyzed_result = new TreeMap<>();
        List<String> _temp = new ArrayList<>();
        List<Object> _items = new ArrayList<>();

        for (Map.Entry<String, String> map_result : date_time_map.entrySet()) {
            String _key = map_result.getKey();
            String _value = map_result.getValue();

            _items.add(_key);
            _items.add(_value);
            System.out.println(_key + "/" + _value);
        }

        for (Map.Entry<String, LinkedHashSet<String>> map_result : entities_map.entrySet()) {
            String _key = map_result.getKey();
            LinkedHashSet<String> _value = map_result.getValue();

            _items.add(_key);
            _items.add(_value);
            System.out.println(_key + "/" + _value);
        }

        System.out.println();
        System.out.println("NLP" + _items);

        for (Map.Entry<String, String> map_result : sentence_token_map.entrySet()) {
            String _key = map_result.getKey();
            String _value = map_result.getValue();

            TimeValidator time_valid = new TimeValidator();
            boolean is_valid = time_valid.validate(_key);
            boolean is_match = _key.matches(TIME24HOURS_PATTERN);

            if (is_match && is_valid && _value.equalsIgnoreCase("TIME")) {
                _temp.add(_key);
            }
        }

        System.out.println(_temp);
    }
}
