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

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.time.TimeAnnotator;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NLPLoadEngine {
    public StanfordCoreNLP _pipeline;
    private static NLPLoadEngine instance = null;
    private static final String CLASSIFIER_MODEL = "classifiers/english.muc.7class.distsim.crf.ser.gz";
    private static final String ENGLISHPCFG_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
    public static CRFClassifier<CoreLabel> CLASSIFIER = null;
    public LexicalizedParser LEXICAL_PARSER = null;
    private Logger _logger = Logger.getLogger("--- NLP LoadEngine Log ---");
    private PrintStream _err = System.err;
    private double COUNTER = 0;
    private double LOAD_COUNT = 36.76;

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
                printProgressBar(COUNTER / LOAD_COUNT);
                COUNTER++;
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
     * This method prints the progress bar to be displayed.
     * [Hack] to inject into printstream overriding all red warnings on load with progress bar
     *
     * @param _percent
     */
    public void printProgressBar(double _percent) {
        StringBuilder bar = new StringBuilder("[");

        for (int i = 0; i < 50; i++) {
            if (i < (_percent / 2)) {
                bar.append("=");
            } else if (i == (_percent / 2)) {
                bar.append(">");
            } else {
                bar.append(" ");
            }
        }
        try {
        	 final String os = System.getProperty("os.name");
        	 if (os.contains("Windows"))
             {
                 Runtime.getRuntime().exec("cls");
             }
             else
             {
                 Runtime.getRuntime().exec("clear");
             }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        bar.append("]   Loading... " + _percent + "%     ");
        System.out.print("\r" + bar.toString());
    }

    public NLPLoadEngine() {
        this.mute();
        Properties _properties = new Properties();
        _properties.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");

        try {
            CLASSIFIER = CRFClassifier.getClassifierNoExceptions(CLASSIFIER_MODEL);
            LEXICAL_PARSER = LexicalizedParser.loadModel(ENGLISHPCFG_MODEL);
            _pipeline = new StanfordCoreNLP(_properties, true);
            _pipeline.addAnnotator(new TimeAnnotator("sutime", _properties));

            _logger.log(Level.INFO, "Successfully loaded models.");
        } catch (RuntimeException ex) {
            _logger.log(Level.SEVERE, "Error loading models.");
            throw ex;
        }
    }

    public static NLPLoadEngine getInstance() {
        if (instance == null) {
            instance = new NLPLoadEngine();
        }

        return instance;
    }

    public CRFClassifier<CoreLabel> classifierModel() {
        return CLASSIFIER;
    }
}
