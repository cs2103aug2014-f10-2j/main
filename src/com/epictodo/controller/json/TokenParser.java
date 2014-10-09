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

package com.epictodo.controller.json;

import java.io.IOException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class TokenParser {
    /**
     * This method handles an Object. Consume the first token which is BEGIN_OBJECT.
     * The Object could exist either an array or non array tokens.
     *
     * Peek() is used to find out the type of the next token without actually consuming it.
     *
     * This example invokes the handler the full Json Object. The first token in JsonToken.BEGIN_OBJECT => always true.
     *
     * Usage Example:
     * JsonReader _reader = new JsonReader(new StringReader(json));
     * jsonObjectHandler(_reader);
     *
     * @param _reader
     * @throws IOException
     */
    public static void jsonObjectHandler(JsonReader _reader) throws IOException {
        _reader.beginObject();

        while (_reader.hasNext()) {
            JsonToken _token = _reader.peek();

            if (_token.equals(JsonToken.BEGIN_ARRAY)) {
                arrayTokensHandler(_reader);
            } else if (_token.equals(JsonToken.END_ARRAY)) {
                _reader.endObject();
                return;
            } else { nonArrayTokensHandler(_reader, _token); }
        }
    }

    /**
     * This method handles non-array non-object tokens.
     *
     * @param _reader
     * @param _token
     * @throws IOException
     */
    private static void nonArrayTokensHandler(JsonReader _reader, JsonToken _token) throws IOException {
        if (_token.equals(JsonToken.NAME)) {
            System.out.println(_reader.nextName());
        } else if (_token.equals(JsonToken.STRING)) {
            System.out.println(_reader.nextString());
        } else if (_token.equals(JsonToken.NUMBER)) {
            System.out.println(_reader.nextDouble());
        } else {
            _reader.skipValue();
        }
    }

    /**
     * This method handles a json array.
     * The first token would be JsonToken.BEGIN_ARRAY.
     * Arrays may contain objects or primitives.
     *
     * @param _reader
     * @throws IOException
     */
    private static void arrayTokensHandler(JsonReader _reader) throws IOException {
        _reader.beginArray();

        while (true) {
            JsonToken _token = _reader.peek();

            if (_token.equals(JsonToken.END_ARRAY)) {
                _reader.endArray();
                break;
            } else if (_token.equals(JsonToken.BEGIN_OBJECT)) {
                jsonObjectHandler(_reader);
            } else { nonArrayTokensHandler(_reader, _token); }
        }
    }
}
