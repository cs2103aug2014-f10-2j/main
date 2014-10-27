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

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.time.TimeAnnotations;
import edu.stanford.nlp.time.TimeAnnotator;
import edu.stanford.nlp.time.TimeExpression;
import edu.stanford.nlp.util.CoreMap;

import java.text.SimpleDateFormat;
import java.util.*;

public class SentenceAnalysis {
    protected StanfordCoreNLP _pipeline;

    public SentenceAnalysis() {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
        _pipeline = new StanfordCoreNLP(props);
        _pipeline.addAnnotator(new TimeAnnotator("sutime", props));
    }

    /**
     * This method analyzes the date and time format in a sentence
     * The analyzer will extract the date and time structure from the sentence into tokens
     *
     * @param _sentence
     * @return _results
     */
    public List<String> dateTimeAnalyzer(String _sentence) {
        List<String> _results = new LinkedList<>();

        Annotation _document = new Annotation(_sentence);
        _document.set(CoreAnnotations.DocDateAnnotation.class, getTodayDate());
        _pipeline.annotate(_document);
        List<CoreMap> timex_annotations = _document.get(TimeAnnotations.TimexAnnotations.class);

        for (CoreMap _tokens : timex_annotations) {
            _tokens.get(CoreAnnotations.TokensAnnotation.class);
            _results.add(_tokens.toString());
            _results.add(_tokens.get(TimeExpression.Annotation.class).getTemporal().toString());
        }

        return _results;
    }

    /**
     * This method analyzes the sentence structure and returns a Map of word token and NER token
     *
     * @param _sentence
     * @return _results
     */
    public Map<String, String> sentenceAnalyzer(String _sentence) {
        Map<String, String> _results = new HashMap<>();
        Annotation _document = new Annotation(_sentence);
        _document.set(CoreAnnotations.DocDateAnnotation.class, getTodayDate());
        _pipeline.annotate(_document);
        List<CoreMap> _sentences = _document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : _sentences) {
            // Traverse the tokens of words in the current sentence
            for (CoreLabel _tokens : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // Text of the token
                String word = _tokens.get(CoreAnnotations.TextAnnotation.class);
                // POS tag of the token
                String pos = _tokens.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                // NER label of the token
                String ner = _tokens.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                _results.put(word, ner);
            }
        }

        return _results;
    }

    /**
     * This method identify and extract NER entities such as Name, Person, Date, Time, Organization, Location
     *
     * @param _sentence
     * @param _model    - Stanford model names out of the three models
     * @return _results
     */
    public LinkedHashMap<String, LinkedHashSet<String>> nerEntitiesExtractor(String _sentence, String _model) {
        LinkedHashMap<String, LinkedHashSet<String>> _results = new <String, LinkedHashSet<String>>LinkedHashMap();
        CRFClassifier<CoreLabel> _classifier = CRFClassifier.getClassifierNoExceptions(_model);
        List<List<CoreLabel>> _classify = _classifier.classify(_sentence);

        for (List<CoreLabel> _tokens : _classify) {
            for (CoreLabel _token : _tokens) {
                String _word = _token.word();
                String _category = _token.get(CoreAnnotations.AnswerAnnotation.class);

                if (!"O".equals(_category)) {
                    if (_results.containsKey(_category)) {
                        // Key already exists, insert to LinkedHashMap
                        _results.get(_category).add(_word);
                    } else {
                        LinkedHashSet<String> _temp = new LinkedHashSet<>();
                        _temp.add(_word);
                        _results.put(_category, _temp);
                    }
                }
            }
        }

        return _results;
    }

    private String getTodayDate() {
        Date _date = new Date();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        return date_format.format(_date);
    }
}
