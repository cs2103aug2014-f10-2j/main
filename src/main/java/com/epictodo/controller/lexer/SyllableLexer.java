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

import com.epictodo.model.Word;

public class SyllableLexer {
    public int count(Word _word) {
        final String word_str = _word.getText();
        int _count = 0;
        boolean is_vowel = false;

        if(word_str.isEmpty()) { return 0; }

        /**
         * Check each word for vowels.
         */
        for (int i = 0; i < word_str.length(); i++) {
            if (isVowel(word_str.charAt(i)) && !is_vowel) {
                is_vowel = true;
                _count++;
            } else if (isVowel(word_str.charAt(i)) && is_vowel) {
                is_vowel = true;
            } else {
                is_vowel = false;
            }
        }

        /**
         * Check if last character contains an 'e' || 'E'
         */
        char last_char = word_str.charAt(word_str.length() - 1);
        if ((last_char == 'e' || last_char == 'E') && _count != 1) {
            _count--;
        }

        return _count;
    }

    public boolean isVowel(char char_vowel) {
        return char_vowel == 'a' || char_vowel == 'A'
                || char_vowel == 'e' || char_vowel == 'E'
                || char_vowel == 'i' || char_vowel == 'I'
                || char_vowel == 'o' || char_vowel == 'O'
                || char_vowel == 'u' || char_vowel == 'U'
                || char_vowel == 'y' || char_vowel == 'Y';
    }
}
