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

import com.epictodo.model.Task;
import com.epictodo.model.Task.TaskType;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage {
    private static Logger _logger = Logger.getLogger("--- Storage Parser Log ---");
    private static final String file_name = "storage.txt";

    /**
     * This method instantiates a GSON Object.
     * Method will read JSON Object from memory and translates to JSON.
     *
     * @return _gson
     */
    private static Gson instantiateObject() {
        GsonBuilder gson_builder = new GsonBuilder();
        gson_builder.setPrettyPrinting()
                    .serializeNulls()
                    .disableHtmlEscaping()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

        Gson _gson = gson_builder.create();

        return _gson;
    }

    /**
     * This method saves Map<TaskType, List<Task>> from memory object to Json file.
     *
     * @param file_name
     * @param _map
     * @return true
     */
    public static boolean saveToJson(String file_name, Map<TaskType, List<Task>> _map) {
        assert file_name.equalsIgnoreCase(file_name);
        _logger.log(Level.INFO, "Filename: \'storage.txt\' has been asserted.");

        try {
            FileWriter file_writer = new FileWriter(file_name);
            Gson _gson = instantiateObject();
            String json_result = _gson.toJson(_map);

            if (_map == null || _map.isEmpty()) {
                _logger.log(Level.WARNING, "Map<TaskType, List<Task>> is empty.");
                file_writer.write("");
            } else {
                file_writer.write(json_result);
                _logger.log(Level.INFO, "Successfully stored JSON results to Storage");
            }

            file_writer.close();
        } catch(IOException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * This method loads the Json file to Map<TaskType, List<Task>> of memory objects
     *
     * @param file_name
     * @return _result
     */
    public static Map<TaskType, List<Task>> loadDbFile(String file_name) {
        Map<TaskType, List<Task>> _result;
        assert file_name.equalsIgnoreCase(file_name);
        _logger.log(Level.INFO, "Filename: \'storage.txt\' has been asserted.");

        try {
            FileReader _reader = new FileReader(file_name);
            Gson _gson = instantiateObject();
            TypeToken<Map<TaskType, List<Task>>> type_token = new TypeToken<Map<TaskType, List<Task>>>(){};

            _result = _gson.fromJson(_reader, type_token.getType());
        } catch(IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return _result;
    }
}
