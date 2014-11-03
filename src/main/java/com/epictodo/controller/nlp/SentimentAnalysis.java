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

import com.epictodo.engine.NLPLoadEngine;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;

public class SentimentAnalysis {
    protected StanfordCoreNLP _pipeline;
    private NLPLoadEngine load_engine = NLPLoadEngine.getInstance();

    public SentimentAnalysis() {
        this._pipeline = load_engine._pipeline;
//        Properties _properties = new Properties();
//        _properties.put("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
//        _pipeline = new StanfordCoreNLP(_properties);
    }

    /**
     * This method will sentimentalyze the sentiments of a sentence structure.
     *
     * @param _sentence
     * @return _sentiment
     */
    public String sentimentalyze(String _sentence) {
        int sentiment_score = 0;
        int average_score = 0;
        int _count = 0;
        int sentiment;
        String _sentiment = "";

        // Create an empty Annotation with the sentence
        Annotation annotate_sentence = new Annotation(_sentence);

        // Run all annotation
        _pipeline.annotate(annotate_sentence);
        Annotation process_sentence = _pipeline.process(_sentence);
        List<CoreMap> sentences = annotate_sentence.get(CoreAnnotations.SentencesAnnotation.class);

        if (_sentence != null || _sentence.length() < 0) {
            for (CoreMap sentence : sentences) {
                // Traverse the words in the current sentence
                for (CoreLabel _token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                    // Text of the token
                    _token.get(CoreAnnotations.TextAnnotation.class);
                    // Part-of-Speech of the token
                    _token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                    // Named Entity of the token
                    _token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                }

                // Sentiment Analyzer of the sentence
                _sentiment = sentence.get(SentimentCoreAnnotations.ClassName.class);
            }

            for (CoreMap sentence : process_sentence.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree _tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
                sentiment = RNNCoreAnnotations.getPredictedClass(_tree);

                _count++;
                average_score += sentiment;
            }

            sentiment_score = (average_score / _count);
            _sentiment = calculateSentiment(sentiment_score);
        }

        return _sentiment;
    }

    /**
     * This method displays the sentiment score ratings of each sentiment
     * The sentiment score is mapped to an individual sentiment
     *
     * @param _sentiment
     * @return
     */
    private String calculateSentiment(int _sentiment) {
        switch (_sentiment) {
            case 0:
                return "Very Negative";
            case 1:
                return "Negative";
            case 2:
                return "Neutral";
            case 3:
                return "Positive";
            case 4:
                return "Very Positive";
            default:
                return "";
        }
    }
}
