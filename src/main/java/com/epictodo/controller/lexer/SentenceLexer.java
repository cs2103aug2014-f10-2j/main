//@author A0111875E-unused
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

import com.epictodo.model.nlp.Sentence;

import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class SentenceLexer implements ISentenceLexer {
    public List<Sentence> tokenize(String _text) {
        List<Sentence> _sentences = new LinkedList<>();
        BreakIterator _border = BreakIterator.getSentenceInstance(Locale.US);
        _border.setText(_text);

        int _start = _border.first();
        // Iterate, creating sentences out of all the Strings between the given boundaries
        for (int _end = _border.next(); _end != BreakIterator.DONE; _start = _end, _end = _border.next()) {
            _sentences.add(new Sentence(_text.substring(_start, _end)));
        }

        return _sentences;
    }
}
