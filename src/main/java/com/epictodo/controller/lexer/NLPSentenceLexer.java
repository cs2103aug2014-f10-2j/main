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

package com.epictodo.controller.lexer;

import com.epictodo.model.Sentence;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class NLPSentenceLexer implements ISentenceLexer {
    @Override
    public List<Sentence> tokenize(String _text) {
        List<Sentence> _sentences = new ArrayList<>();
        final DocumentPreprocessor document_preprocessor = new DocumentPreprocessor(new StringReader(_text));

        for (List<HasWord> sentence : document_preprocessor) {
            _sentences.add(new Sentence(sentence.toString()));
        }

        return _sentences;
    }
}
