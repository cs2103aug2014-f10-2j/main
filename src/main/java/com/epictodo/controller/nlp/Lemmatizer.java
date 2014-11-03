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
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.LinkedList;
import java.util.List;

public class Lemmatizer {
    protected StanfordCoreNLP _pipeline;
    private NLPLoadEngine load_engine = NLPLoadEngine.getInstance();

    public Lemmatizer() {
        this._pipeline = load_engine._pipeline;
//        Properties _properties = new Properties();
//        _properties.put("annotators", "tokenize, ssplit, pos, lemma");
//
//        this._pipeline = new StanfordCoreNLP(_properties);
    }

    /**
     * This method will lemmatize the sentence into individual lemmatized tokens before storing into List<String>
     *
     * @param _sentence
     * @return _lemmas
     */
    public List<String> lemmatize(String _sentence) {
        List<String> _lemmas = new LinkedList<>();
        Annotation annotate_sentence = new Annotation(_sentence);
        _pipeline.annotate(annotate_sentence);

        // Iterate over all of the sentences found
        List<CoreMap> sentences = annotate_sentence.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            // Iterate over all tokens in a sentence
            for (CoreLabel _tokens : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // Retrieve and add lemma for each word into a List of lemmas
                _lemmas.add(_tokens.get(CoreAnnotations.LemmaAnnotation.class));
            }
        }

        return _lemmas;
    }
}
