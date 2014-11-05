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
import com.epictodo.controller.nlp.SentenceStructure;
import com.epictodo.util.DateValidator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.*;

public class NLPEngine {
    protected static StanfordCoreNLP _pipeline;
    private PrintStream _err = System.err;
    private NLPLoadEngine load_engine = NLPLoadEngine.getInstance();
    private DateValidator date_validator = DateValidator.getInstance();
    private SentenceAnalysis sentence_analysis = new SentenceAnalysis();
    private SentenceStructure sentence_struct = new SentenceStructure();

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
     * <p/>
     * Assumptions:
     * 1. PERSON name has to start with a capital letter. For example, "Kenneth"
     * 2. Does not handle complex time such as "From 09:00 to 14:00"
     * 3. Priority is determined by weekly basis from today's date. Priority increases every week.
     * 4. Time has to follow the format (hh:mm)
     *
     * @param _sentence
     * @throws ParseException
     */
    public List<String> flexiAdd(String _sentence) throws ParseException {
        String tomorrow_date;
        String date_value;
        String time_value;
        String _priority;
        int num_days;

        Map<String, String> date_time_map = sentence_analysis.dateTimeAnalyzer(_sentence);
        Map<String, String> sentence_token_map = sentence_analysis.sentenceAnalyzer(_sentence);
        LinkedHashMap<String, LinkedHashSet<String>> entities_map = sentence_analysis.nerEntitiesExtractor(_sentence);
        Map<String, String> sentence_struct_map = sentence_struct.sentenceDependencies(_sentence);
        List<String> analyzed_results = new ArrayList<>();
        List<Object> _items = new ArrayList<>();

        for (Map.Entry<String, LinkedHashSet<String>> map_result : entities_map.entrySet()) {
            String _key = map_result.getKey();
            LinkedHashSet<String> _value = map_result.getValue();

            _items.add(_key);
            _items.add(_value);
        }

        for (Map.Entry<String, String> map_result : sentence_token_map.entrySet()) {
            String _key = map_result.getKey();
            String _value = map_result.getValue();

            if (_value.equalsIgnoreCase("PERSON")) {
                if (!_key.equalsIgnoreCase("Prof")) {
                    analyzed_results.add(_key);
                } else {
                    analyzed_results.add(_key);
                }
            } else if (_value.equalsIgnoreCase("LOCATION")) {
                analyzed_results.add(_key);
            }
        }

        for (Map.Entry<String, String> map_result : date_time_map.entrySet()) {
            String _key = map_result.getKey();
            String _value = map_result.getValue();

            _items.add(_key);
            _items.add(_value);

            tomorrow_date = date_validator.getDateToCompare(_value);
            num_days = date_validator.compareDate(tomorrow_date);

            if (num_days >= 0 && num_days <= 1) {
                _priority = date_validator.determinePriority(tomorrow_date);
                date_value = date_validator.getDateInFormat(_value);
                time_value = date_validator.getTimeInFormat(_value);

                analyzed_results.add(_key);
                analyzed_results.add(date_value);
                analyzed_results.add(time_value);
                analyzed_results.add(_priority);
            } else {
                _priority = date_validator.determinePriority(_value);
                date_value = date_validator.validateDate(_value);
                time_value = date_validator.validateTime(_value);

                analyzed_results.add(_key);
                analyzed_results.add(date_value);
                analyzed_results.add(time_value);
                analyzed_results.add(_priority);
            }
        }

        for (Map.Entry<String, String> map_result : sentence_struct_map.entrySet()) {
            String _key = map_result.getKey();
            String _value = map_result.getValue();

            if (_key.equalsIgnoreCase("root") || _key.equalsIgnoreCase("dep") || _key.equalsIgnoreCase("dobj") ||
                    _key.equalsIgnoreCase("prep_on")) {
                analyzed_results.add(_value);
            }
        }

        return analyzed_results;
    }
}
