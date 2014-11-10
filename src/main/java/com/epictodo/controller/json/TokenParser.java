//@author A0111875E-unused
package com.epictodo.controller.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;

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

    public String searchTdoken(String _json, String search_param) {
        JsonParser _parser = new JsonParser();
        // The JsonElement is the root node.
        // It can be an object, array, null or java primitive.
        JsonElement _element = _parser.parse(_json);
        String search_result = null;
        
        if (_element.isJsonObject()) {
            JsonObject _storage = _element.getAsJsonObject();
            search_result = _storage.get(search_param).getAsString();
        }

        return search_result;
    }
}
