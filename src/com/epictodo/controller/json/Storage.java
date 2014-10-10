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

import com.epictodo.model.*;
import java.io.*;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Storage {
    /*
	 * This method instantiates a GSON Object.
	 * Method will read JSON Object from memory and translates to JSON.
	 *
	 * @return      GSON object
	 */
    private static Gson instantiateObject() {
        GsonBuilder gson_builder = new GsonBuilder();
        gson_builder.setPrettyPrinting()
                    .serializeNulls()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

        Gson _gson = gson_builder.create();

        return _gson;
    }

    public static boolean saveToJson(String file_name, ArrayList<Task> array_list) {
        try {
            FileWriter file_writer = new FileWriter(file_name);
            Gson _gson = instantiateObject();
            String json_result = _gson.toJson(array_list);

            if (array_list.isEmpty()) {
                file_writer.write("");
            } else {
                file_writer.write(json_result);
            }

            file_writer.close();
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public static ArrayList<Task> loadDbFile(String file_name) {
        ArrayList<Task> _result = new ArrayList<Task>();

        try {
            FileReader _reader = new FileReader(file_name);
            Gson _gson = instantiateObject();
            TypeToken<ArrayList<Task>> type_token = new TypeToken<ArrayList<Task>>(){};

            _result = _gson.fromJson(_reader, type_token.getType());
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return _result;
    }
}
