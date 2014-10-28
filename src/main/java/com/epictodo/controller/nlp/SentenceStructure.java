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

import com.epictodo.util.TimeValidator;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SentenceStructure {
    protected StanfordCoreNLP _pipeline;
    private static Logger _logger = Logger.getLogger("--- SentenceStructure Log ---");
    private TreebankLanguagePack treebank_languagepack = new PennTreebankLanguagePack();
    private GrammaticalStructureFactory grammarical_structure_factory = treebank_languagepack.grammaticalStructureFactory();
    private GrammaticalStructure grammartical_structure;
    private TypedDependency type_dependency;

    public SentenceStructure() {
        Properties _properties = new Properties();
        _properties.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        _pipeline = new StanfordCoreNLP(_properties);
    }

    /**
     * This method extracts the dependency name from the sentence tokens based on the dependency node relation
     *
     * @param _sentence
     * @param dependency_reln
     * @return dependency_obj
     */
    public Map<String, String> sentenceDependencies(String _sentence) {
        Map<String, String> dependency_obj = new HashMap<>();
        // Create an empty Annotation with the sentence
        Annotation annotate_sentence = new Annotation(_sentence);

        // Run all annotation
        _pipeline.annotate(annotate_sentence);
        List<CoreMap> sentences = annotate_sentence.get(CoreAnnotations.SentencesAnnotation.class);

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

            // Parsed tree of the current sentence
            Tree _tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);

            // Semantic graph dependency of the current sentence
            SemanticGraph _dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);

            grammartical_structure = grammarical_structure_factory.newGrammaticalStructure(_tree);
            Collection<TypedDependency> collection_typedependency = grammartical_structure.typedDependenciesCollapsed();
            Object[] _list = collection_typedependency.toArray();

            for (Object _object : _list) {
                type_dependency = (TypedDependency) _object;

                dependency_obj.put(type_dependency.reln().toString(), type_dependency.dep().nodeString());
//                if (type_dependency.reln().equals(dependency_reln)) {
//                    dependency_obj.put(type_dependency.reln().toString(), type_dependency.dep().nodeString());
//                }
            }
        }

        return dependency_obj;
    }

    /**
     * This method extracts the date & time of the sentence tokens to be parsed
     *
     * @param _sentence
     * @return dependency_obj
     */
    public List<String> dateTimeDepExtract(String _sentence) {
        List<String> dependency_obj = new LinkedList<>();
        // Create an empty Annotation with the sentence
        Annotation annotate_sentence = new Annotation(_sentence);

        // Run all annotation
        _pipeline.annotate(annotate_sentence);
        List<CoreMap> sentences = annotate_sentence.get(CoreAnnotations.SentencesAnnotation.class);

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

            // Parsed tree of the current sentence
            Tree _tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);

            // Semantic graph dependency of the current sentence
            SemanticGraph _dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);

            grammartical_structure = grammarical_structure_factory.newGrammaticalStructure(_tree);
            Collection<TypedDependency> collection_typedependency = grammartical_structure.typedDependenciesCollapsed();
            Object[] _list = collection_typedependency.toArray();

            for (Object _object : _list) {
                type_dependency = (TypedDependency) _object;

                if (checkDateFormat(type_dependency.dep().nodeString()) == true || checkTimeFormat(type_dependency.dep().nodeString())) {
                    dependency_obj.add(type_dependency.dep().nodeString());
                }
            }
        }

        return dependency_obj;
    }

    /**
     * This method checks the given string if it's in the date format based on Regex matches
     *
     * @param _sentence
     * @return boolean
     */
    private boolean checkDateFormat(String _sentence) {
        if (_sentence.length() > 4 && _sentence.matches("[0-9]+")) {
            return true;
        }

        return false;
    }

    /**
     * This method checks on the given string if it's in the time format based on Regex matches
     * Time format must be strictly be kept to hhmmh / hhmmhr / hhmmhrs
     * 
     * Example:
     * 0900h, 1030hr, 1330hrs (PASSED)
     * 0900, 1030 h, 1330pm (FAILED)
     *
     * @param _sentence
     * @return boolean
     */
    private boolean checkTimeFormat(String _sentence) {
        TimeValidator time_valid = new TimeValidator();

        if (time_valid.validate(_sentence) == true) {
            if (_sentence.length() > 4 && (_sentence.matches("[0-9]+h") || _sentence.matches("[0-9]+h\\S*") || _sentence.matches("[0-9]+hr") ||
                    _sentence.matches("[0-9]+hrs") || _sentence.matches("[0-9]+am") || _sentence.matches("[0-9]+pm"))) {
                return true;
            }
        } else {
            _logger.log(Level.WARNING, "Time format doesn't match (hhmmhr / hhmmhrs)");

            return false;
        }

        return false;
    }
}
