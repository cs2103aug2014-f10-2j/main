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

import com.epictodo.model.nlp.Delta;
import com.epictodo.model.nlp.Response;
import com.epictodo.model.nlp.Search;
import com.epictodo.util.DateValidator;
import com.epictodo.util.TimeValidator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.TypedDependency;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.*;

public class NLPEngine {
    protected static StanfordCoreNLP _pipeline;
    private static NLPEngine instance = null;
    private TimeValidator time_validator = TimeValidator.getInstance();
    private DateValidator date_validator = DateValidator.getInstance();
    private SentenceAnalysis sentence_analysis = new SentenceAnalysis();
    private SentenceStructure sentence_struct = new SentenceStructure();
    private GrammaticalParser grammartical_parser = new GrammaticalParser();
    private Response _response = new Response();
    private Delta _delta = new Delta();
    private Search _search = new Search();
    private PrintStream _err = System.err;
    private int CAPACITY = 1000;
    private final String DATE_PATTERN = "(\\d+)";

    public NLPEngine() {
        NLPLoadEngine load_engine = NLPLoadEngine.getInstance();
        _pipeline = load_engine._pipeline;
    }

    /**
     * This method ensures that there will only be one running instance
     *
     * @return
     */
    public static NLPEngine getInstance() {
        if (instance == null) {
            instance = new NLPEngine();
        }

        return instance;
    }

    /**
     * This method mutes NLP API Error Messages temporarily
     * This works for all console messages
     * <p/>
     * Usage:
     * <p/>
     * mute();
     */
    public void mute() {
        System.setErr(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));
    }

    /**
     * This method restores NLP API Error Messages to be displayed
     * This method works for all console messages.
     * <p/>
     * Usage:
     * <p/>
     * restore();
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
     * 2. Priority is determined by weekly basis from today's date. Priority increases every week.
     * <p/>
     * 3. Time has to strictly follow the format (hh:mm)
     * 3.1 10:00 is accepted
     * 3.2 1000 is not accepted
     * <p/>
     * 4. PERSON name cannot be named after a month (for example, April, May, June) -> Registers as a DATE
     * 5. PERSON name cannot be named after a day (for example, Sunday) -> Registers as a DATE
     * <p/>
     * 6. Time period: Day cannot be placed after the time period
     * 6.1. Tuesday 2 weeks later (PASSED) - gets the Tuesday 2 weeks later
     * 6.2. 2 weeks later Tuesday (FAILED) - gets 2 weeks later from today's date
     * <p/>
     * 7. There can be only 2 time input which will be allowed to be parsed in. Additional time will be rejected.
     * 7.1. from 10:00 to 14:00 then 18:00 > reflects only [10:00, 14:00] as start_time and end_time
     * <p/>
     * 8. If users input date in quick date for example, (01/12/2014 -> dd/MM/yyyy)
     * 8.1. [Issue] Manual correction of storing through validation
     * <p/>
     * 9. Chinese names will be read as two different PERSON. For example, Jie Ning will be read as 'Jie', 'Ning'
     * 10. Supports one short date, for example '14/11/2014'
     * <p/>
     * Usage:
     * <p/>
     * 1. flexiAdd("meeting with Damith on project submission Tuesday 2 weeks later at 10:34");
     * 2. flexiAdd("project submission for cs2103 next Tuesday from 10:00 to 14:00 to Damith");
     * <p/>
     * Output:
     * <p/>
     * 1.
     * Task Name: meeting submission
     * Task Desc: Damith Tuesday 2 weeks later at 10:34
     * Task Date: 181114
     * Task Time: 10:34
     * Task Priority: 8
     * Task Start Time: null
     * Task End Time: null
     * Task Duration: 0.0
     * <p/>
     * 2.
     * Task Name: submission cs2103
     * Task Desc: Damith 10:00 14:00 next Tuesday
     * Task Date: 111114
     * Task Time: null
     * Task Priority: 9
     * Task Start Time: 10:00
     * Task End Time: 14:00
     * Task Duration: 4.0
     *
     * @param _sentence
     * @throws ParseException
     */
    public Response flexiAdd(String _sentence) throws ParseException {
        boolean is_date_set = false;
        boolean is_time_set = false;
        boolean is_priority_set = false;
        String tomorrow_date;
        String date_value;
        String time_value;
        String _priority;
        String start_time;
        String end_time;
        double _duration;
        int num_days;

        List<String> analyzed_results = new ArrayList<>();
        List<String> task_name = new ArrayList<>();
        List<String> task_desc = new ArrayList<>();
        List<String> task_date = new ArrayList<>();
        List<String> task_time = new ArrayList<>();
        List<String> date_list = new ArrayList<>();

        StringBuilder string_builder = new StringBuilder(CAPACITY);

        // Initialize Sentence Structure & Sentence Analysis to Map
        Map<String, String> sentence_struct_map = sentence_struct.sentenceDependencies(_sentence);
        Map<String, String> date_time_map = sentence_analysis.dateTimeAnalyzer(_sentence);
        Map<String, String> sentence_token_map = sentence_analysis.sentenceAnalyzer(_sentence);
        LinkedHashMap<String, LinkedHashSet<String>> entities_map = sentence_analysis.nerEntitiesExtractor(_sentence);
        List<TypedDependency> grammar_struct_map = grammartical_parser.grammarAnalyzer(_sentence);

        /**
         * Name Entity Recognition (NER) map
         * For loop to traverse key:value of NER map to retrieve keywords to be processed
         *
         * Example: root, nn, dobj, nsubj, aux, xcomp, prep, num, etc.
         *
         */
        for (Map.Entry<String, LinkedHashSet<String>> map_result : entities_map.entrySet()) {
            String _key = map_result.getKey();
            LinkedHashSet<String> _value = map_result.getValue();
        }

        /**
         * Sentence analyzer to analyze the text for keywords
         * For loop to traverse the following:
         *
         * 1. (TIME) - stores TIME's (value) key to check if it's a single or multiple TIME value
         * 1.1. Checks if key is valid time before storing.
         * 1.2. "at" maybe classified as a valid TIME but we do not want to register this
         *
         * 2. (PERSON) - stores PERSON name to be processed in Task Description
         * 2.1. PERSON does not accept 'Prof' as an actual person, rather an entity
         *
         * 3. (LOCATION) - stores LOCATION name if it exists.
         * 3.1. TOKYO, SINGAPORE are actual LOCATION
         * 3.2. COM1, LT15, SR1 are not an actual LOCATION stored in our model (can be implemented using Classifier in future)
         *
         */
        for (Map.Entry<String, String> map_result : sentence_token_map.entrySet()) {
            String _key = map_result.getKey();
            String _value = map_result.getValue();

            if (_value.equalsIgnoreCase("TIME")) {
                if (time_validator.validate(_key)) {
                    task_time.add(_key);
                }
            }

            if (_value.equalsIgnoreCase("NUMBER")) {
                if (date_validator.validateDateExpression(_key)) {
                    date_value = date_validator.fixShortDate(_key);
                    task_date.add(date_value);
                    num_days = date_validator.compareDate(date_value);

                    // Checks if date distance is >= 0 or <= 1 of 1 day
                    if (num_days >= 0 && num_days <= 1) {
                        _priority = date_validator.determinePriority(date_value);
                        date_value = date_validator.genericDateFormat(date_value);

                        if (_response.getTaskDate() == null) {
                            _response.setTaskDate(date_value);
                        }

                        _response.setPriority(Integer.parseInt(_priority));
                        is_priority_set = true;
                    } else { // Check if TaskDate has been set previously, prevent override
                        _priority = date_validator.determinePriority(date_value);
                        date_value = date_validator.genericDateFormat(date_value);

                        if (_response.getTaskDate() == null) {
                            _response.setTaskDate(date_value);
                        }

                        _response.setPriority(Integer.parseInt(_priority));
                        is_priority_set = true;
                    }

                    is_date_set = true;
                } else {
                    date_value = date_validator.nlpShortDate(_key);
                    task_date.add(date_value);
                    num_days = date_validator.compareDate(date_value);

                    // Checks if date distance is >= 0 or <= 1 of 1 day
                    if (num_days >= 0 && num_days <= 1) {
                        _priority = date_validator.determinePriority(date_value);
                        date_value = date_validator.genericDateFormat(date_value);

                        if (_response.getTaskDate() == null) {
                            _response.setTaskDate(date_value);
                        }

                        _response.setPriority(Integer.parseInt(_priority));
                        is_priority_set = true;
                    } else { // Check if TaskDate has been set previously, prevent override
                        _priority = date_validator.determinePriority(date_value);
                        date_value = date_validator.genericDateFormat(date_value);

                        if (_response.getTaskDate() == null) {
                            _response.setTaskDate(date_value);
                        }

                        _response.setPriority(Integer.parseInt(_priority));
                        is_priority_set = true;
                    }

                    is_date_set = true;
                }
            }

            if (_value.equalsIgnoreCase("PERSON")) {
                if (!_key.equalsIgnoreCase("Prof")) {
                    task_desc.add(_key);
                    analyzed_results.add(_key);
                } else {
                    task_desc.add(_key);
                    analyzed_results.add(_key);
                }
            } else if (_value.equalsIgnoreCase("LOCATION")) {
                task_desc.add(_key);
                analyzed_results.add(_key);
            } else if (_value.equalsIgnoreCase("ORGANIZATION")) {
                task_desc.add(_key);
                analyzed_results.add(_key);
            }
        }

        /**
         * This algorithm checks if the time values stored are more than or equals to 2
         * There can be instances where users input 3 or more time variables, but the first 2 will be registered
         *
         * This algorithm will getTimeDuration before storing start_time, end_time & _duration to _response
         */
        if (task_time.size() != 0 && task_time.size() >= 2) {
            start_time = task_time.get(0);
            end_time = task_time.get(1);

            _duration = time_validator.getTimeDuration(start_time, end_time);
            _response.setStartTime(start_time);
            _response.setEndTime(end_time);
            _response.setTaskDuration(_duration);

            is_time_set = true;
        } else if (task_time.size() != 0 && task_time.size() < 2) {
            _response.setTaskTime(task_time.get(0));

            is_time_set = true;
        }

        /**
         * Date time analyzer map to analyze date time values to be stored
         * This algorithm will check for TOMORROW date & time distance.
         *
         * If that case happens, the result will be parsed to getDateInFormat & getTimeInFormat to handle such cases
         * Otherwise, the result will be parsed to validateDate & validateTime to handle the more generic cases
         */
        for (Map.Entry<String, String> map_result : date_time_map.entrySet()) {
            String _key = map_result.getKey();
            String _value = map_result.getValue();

            if (!is_date_set) {
                tomorrow_date = date_validator.convertDateFormat(_value);
                num_days = date_validator.compareDate(tomorrow_date);

                // Checks if date distance is >= 0 or <= 1 of 1 day
                if (num_days >= 0 && num_days <= 1) {
                    _priority = date_validator.determinePriority(tomorrow_date);
                    date_value = date_validator.genericDateFormat(tomorrow_date);
                    time_value = date_validator.getTimeInFormat(_value);

                    if (!is_date_set) { // Check if TaskDate has been set previously, prevent override
                        if (date_value.length() == 5) {
                            date_list.add(0, "0");

                            Scanner _scanner = new Scanner(date_value);
                            while (_scanner.hasNext()) {
                                date_list.add(_scanner.next());
                            }
                            _scanner.close();

                            for (String date : date_list) {
                                string_builder.append(date);
                            }

                            _response.setTaskDate(string_builder.toString());
                            is_date_set = true;
                        } else {
                            _response.setTaskDate(date_value);
                            is_date_set = true;
                        }
                    }

                    if (!is_time_set) {
                        _response.setTaskTime(time_value);
                        is_time_set = true;
                    }

                    if (!is_priority_set) {
                        _response.setPriority(Integer.parseInt(_priority));
                        is_priority_set = true;
                    }

//                _response.setTaskDate(date_value);
//                _response.setTaskTime(time_value);
//                _response.setPriority(Integer.parseInt(_priority));
                    task_desc.add(_key);
                    analyzed_results.add(_key);
                    analyzed_results.add(date_value);
                    analyzed_results.add(time_value);
                    analyzed_results.add(_priority);
                } else { // Check if TaskDate has been set previously, prevent override
                    _priority = date_validator.determinePriority(_value);
                    date_value = date_validator.genericDateFormat(tomorrow_date);
                    time_value = date_validator.validateTime(_value);

                    if (!is_date_set) {
                        if (date_value.length() == 5) {
                            date_list.add(0, "0");

                            Scanner _scanner = new Scanner(date_value);
                            while (_scanner.hasNext()) {
                                date_list.add(_scanner.next());
                            }
                            _scanner.close();

                            for (String date : date_list) {
                                string_builder.append(date);
                            }

                            _response.setTaskDate(string_builder.toString());
                            is_date_set = true;
                        } else {
                            _response.setTaskDate(date_value);
                            is_date_set = true;
                        }
                    }

                    if (!is_time_set) {
                        _response.setTaskTime(time_value);
                        is_time_set = true;
                    }

                    if (!is_priority_set) {
                        _response.setPriority(Integer.parseInt(_priority));
                        is_priority_set = true;
                    }

//                _response.setTaskDate(date_value);
//                _response.setTaskTime(time_value);
//                _response.setPriority(Integer.parseInt(_priority));
                    task_desc.add(_key);
                    analyzed_results.add(_key);
                    analyzed_results.add(date_value);
                    analyzed_results.add(time_value);
                    analyzed_results.add(_priority);
                }
            }
        }

        /**
         * Sentence Dependencies map to analyze and return the dependencies of the sentence structure
         * This algorithm checks the dependencies relationship in the tree structure, and returns the results
         */
        for (Map.Entry<String, String> map_result : sentence_struct_map.entrySet()) {
            String _key = map_result.getKey();
            String _value = map_result.getValue();

            if ((_key.equalsIgnoreCase("root") || _key.equalsIgnoreCase("dep") || _key.equalsIgnoreCase("dobj") ||
                    _key.equalsIgnoreCase("prep_on") || _key.equalsIgnoreCase("prep_for") || _key.equalsIgnoreCase("nn") ||
                    _key.equalsIgnoreCase("xcomp")) &&
                    (_value.equalsIgnoreCase("aux"))) {
                task_name.add(_value);
                analyzed_results.add(_value);
            }
        }

        /**
         * Grammartical Struct map analyzes and return the relationship and dependencies of the grammartical structure
         * This algorithm checks for
         *
         * 1. root: the grammatical relation that points to the root of the sentence
         * 1.1. Remove words like 'be'
         * 1.2. Remove words that are already added into the list
         * 2. nn: noun compound modifier is any noun that serves to modify the head noun
         * 2.1. Remove words that is stored already in Task Description
         * 3. nsubj: nominal subject is a noun phrase which is the syntactic subject of a clause
         *    aux: auxiliary is a non-main verb of the clause
         *    xcomp: open clausal complement  is a clausal complement without
         *           its own subject, whose reference is determined by an external subject
         *    dobj: direct object is the noun phrase which is the (accusative) object of the verb
         * 3.1. Add words that does not already exist in Task Name
         * 4. amod: adjectival modifier is any adjectival phrase that serves to modify the meaning of the NP
         * 4.1. Remove words like 'next'
         * 5. prep:  prepositional modifier of a verb, adjective, or noun is any prepositional phrase that serves to
         *           modify the meaning of the verb, adjective, noun, or even another prepositon
         * 5.1. Check Task Time size is more than 1 or less than 2
         * 5.2. Remove Task Time accordingly
         * 6. dep: dependent is when the system is unable to determine a more precise
         *         dependency relation between two words
         * 6.1. Remove words like 'regarding'
         * 7. aux: is a non-main verb of the clause, e.g., a modal auxiliary, or a form of
         *         "be", "do" or "have" in a periphrastic tense
         * 7.1. Remove words like 'to'
         *
         * This algorithm ensures that NLP maintains a certain integrity to making sense while parsing the sentence
         *
         */
        for (TypedDependency type_dependency : grammar_struct_map) {
            String reln_key = type_dependency.reln().getShortName();
            String dep_value = type_dependency.dep().nodeString();

            if (reln_key.equalsIgnoreCase("root")) {
                if (task_name.contains("Be") || task_name.contains("be")) {
                    task_name.remove(dep_value);
                } else if (task_name.contains(dep_value)) {
                    task_name.remove(dep_value);
                } else {
                    task_name.add(dep_value);
                }
            }

            if (reln_key.equalsIgnoreCase("nn")) {
                if (!task_name.contains(dep_value)) {
                    task_name.add(dep_value);
                }

                if (task_desc.contains(dep_value)) {
                    task_name.remove(dep_value);
                }
            }

            if (reln_key.equalsIgnoreCase("nsubj") || reln_key.equalsIgnoreCase("aux") ||
                    reln_key.equalsIgnoreCase("xcomp") || reln_key.equalsIgnoreCase("dobj")) {
                if (!task_name.contains(dep_value)) {
                    task_name.add(dep_value);
                }
            }

            if (reln_key.equalsIgnoreCase("amod") && !dep_value.equalsIgnoreCase("next")) {
                if (!task_name.contains(dep_value)) {
                    task_name.add(dep_value);
                }
            }

            if (reln_key.equalsIgnoreCase("prep")) {
                if (!task_name.contains(dep_value) && (!date_validator.checkDateFormat(dep_value) && !date_validator.checkDateFormat2(dep_value))) {
                    task_name.add(dep_value);
                }

                if (task_time.size() > 0 && task_time.size() < 2) {
                    if (dep_value.contains(task_time.get(0))) {
                        task_name.remove(dep_value);
                    }
                } else if (task_time.size() >= 2 && task_time.size() < 3) {
                    if (dep_value.contains(task_time.get(0)) || dep_value.contains(task_time.get(1))) {
                        task_name.remove(dep_value);
                    }
                }
            }

            if (reln_key.equalsIgnoreCase("dep") && dep_value.equalsIgnoreCase("regarding")) {
                if (task_name.contains(dep_value)) {
                    task_name.remove(dep_value);
                }
            }

            if (reln_key.equalsIgnoreCase("aux") && dep_value.equalsIgnoreCase("to")) {
                if (task_name.contains(dep_value)) {
                    task_name.remove(dep_value);
                }
            }
        }

        _response.setTaskName(task_name);
        _response.setTaskDesc(task_desc);

        return _response;
    }

    /**
     * This method understands the natural input from user's input to edit a task
     * The algorithm analyzes the sentence given, the following are executed.
     * 1. Detects the Task to be edited and the DELTA change of the sentence
     * 2. Process the DELTA changes to flexiAdd in order to analyze the data to be stored
     * 3. Returns a DELTA which consists of a RESPONSE data type to be replaced with the original Task
     * <p/>
     * Assumptions:
     * 1. Users are required to search for the exact TaskName to be edited
     * 2. Sentence will analyze the format of "[ORIGINAL_TASK] to [DELTA]"
     * 2.1. Everything before 'to' will be considered as ORIGINAL_TASK to be replaced
     * 2.2. Everything after 'to' will be considered as DELTA to replace with
     * <p/>
     * Usage:
     * 1. flexisEdit("edit project submission to project report submission by next Thursday at 15:30");
     * [project submission] - ORIGINAL_TASK
     * [project report submission by next Thursday at 15:30] - DELTA
     * <p/>
     * Output:
     * 1.
     * Delta Change: project submission
     * Task Name: project report submission next
     * Task Desc: next Thursday at 15:30
     * Task Date: 131114
     * Task Time: 15:30
     * Task Priority: 9
     * Task Start Time: 12:00
     * Task End Time: 14:00
     * Task Duration: 2.0
     *
     * @param _sentence
     * @return _delta
     * @throws ParseException
     */
    public Delta flexiEdit(String _sentence) throws ParseException {
        boolean is_first = true;
        boolean is_removed = false;
        int _index = 0;

        List<String> sentence_list = new ArrayList<>();
        List<String> delta_list = new ArrayList<>();
        StringBuilder string_builder = new StringBuilder(CAPACITY);

        Scanner _scanner = new Scanner(_sentence);
        while (_scanner.hasNext()) {
            sentence_list.add(_scanner.next());
        }
        _scanner.close();

        /**
         * This algorithm removes words like 'edit' or 'change' which suggests editing of Task
         * The algorithm gets the index of 'to' and stores that index in order to identify ORIGINAL_TASK & DELTA
         *
         */
        for (int i = 0; i < sentence_list.size(); i++) {
            if (is_removed) {
                if (sentence_list.get(i).equalsIgnoreCase("edit") || sentence_list.get(i).equalsIgnoreCase("change")) {
                    sentence_list.remove(i);

                    is_removed = true;
                }
            }

            if (sentence_list.get(i).equalsIgnoreCase("to")) {
                _index = i;
                sentence_list.remove(i);
                i = sentence_list.size();
            }
        }

        /**
         * Removes ORIGINAL_TASK & adds to delta_list
         *
         */
        for (int i = 0; i < _index; i++) {
            delta_list.add(sentence_list.get(0));
            sentence_list.remove(0);
        }

        /**
         * Concatenate sentence List into a String before storing DELTA
         *
         */
        for (String word : sentence_list) {
            if (is_first) {
                is_first = false;
            } else {
                string_builder.append(' ');
            }

            string_builder.append(word);
        }

        _delta.setTaskName(delta_list);
        _delta.setDeltaChange(flexiAdd(string_builder.toString()));

        return _delta;
    }

    /**
     * This method analyzes the sentence structure into SUTIME Annotation.
     * The algorithm facilitates natural language for searching by date.
     * <p/>
     * For instance, "search today" > retrieves today task
     * <p/>
     * Usage:
     * 1. flexiSearch("search today");
     *
     * @param _sentence
     * @throws ParseException
     */
    public Search flexiSearch(String _sentence) throws ParseException {
        String date_value;
        String parse_date;
        StringBuilder string_builder = new StringBuilder(CAPACITY);

        // Initialize Date Analysis to Map
        Map<String, String> date_map = sentence_analysis.dateTimeAnalyzer(_sentence);
        List<String> date_list = new ArrayList<>();

        if (_sentence.isEmpty() || _sentence.equalsIgnoreCase(null) || _sentence.equals("")) {
            _search.setSearchDate(null);
        }

        if (_sentence.matches(DATE_PATTERN)) {
            parse_date = date_validator.nlpShortDate(_sentence);
            parse_date = date_validator.genericDateFormat(parse_date);

            if (parse_date.length() == 5) {
                date_list.add(0, "0");

                Scanner _scanner = new Scanner(parse_date);
                while (_scanner.hasNext()) {
                    date_list.add(_scanner.next());
                }
                _scanner.close();

                for (String date : date_list) {
                    string_builder.append(date);
                }

                _search.setSearchDate(string_builder.toString());
            } else {
                _search.setSearchDate(parse_date);
            }
        } else {
            /**
             * Date analyzer analyzes the sentence in order to map the analyze date values to be searched
             * This algorithm will check for TOMORROW date & time distance.
             *
             */
            for (Map.Entry<String, String> map_result : date_map.entrySet()) {
                String _value = map_result.getValue();

                date_value = date_validator.convertDateFormat(_value);
                date_value = date_validator.genericDateFormat(date_value);

                _search.setSearchDate(date_value);
            }
        }

        return _search;
    }
}
