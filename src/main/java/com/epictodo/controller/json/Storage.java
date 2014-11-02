
package com.epictodo.controller.json;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epictodo.model.DeadlineTask;
import com.epictodo.model.FloatingTask;
import com.epictodo.model.Task;
import com.epictodo.model.TimedTask;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class Storage {
    private static Logger _logger = Logger.getLogger("--- Storage Parser Log ---");
    private static final String file_name = "storage.txt";

    public enum TaskType {
        FLOATING, DEADLINE, TIMED
    };
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
    public static boolean saveToJson(String file_name, ArrayList<Task> list) {
        assert file_name.equalsIgnoreCase(file_name);
        _logger.log(Level.INFO, "Filename: \'storage.txt\' has been asserted.");

        ArrayList<Task> Timedlist = new ArrayList<Task>();
        ArrayList<Task> Deadlinelist = new ArrayList<Task>();
        ArrayList<Task> Floatinglist = new ArrayList<Task>();
        
        for(int i=0; i<list.size(); i++){
        Task t = list.get(i);
        if (t instanceof TimedTask){
        	Timedlist.add(t);
        }
        if (t instanceof DeadlineTask){
        	Deadlinelist.add(t);
        }
        if (t instanceof FloatingTask){
        	Floatinglist.add(t);
        }
        }
        
        Map<TaskType, List<Task>> _map = new HashMap<Storage.TaskType, List<Task>>();
        _map.put(TaskType.TIMED, Timedlist);
        _map.put(TaskType.DEADLINE,Deadlinelist);
        _map.put(TaskType.FLOATING,Floatinglist);
        
        
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

public static ArrayList<Task> loadDbFile(String file_name) {
    ArrayList<Task> _result = new ArrayList<Task>();

    assert file_name.equalsIgnoreCase(file_name);
    _logger.log(Level.INFO, "Filename: \'storage.txt\' has been asserted.");

    try {
        FileReader _reader = new FileReader(file_name);
        Gson _gson = instantiateObject();
        TypeToken<Map<TaskType, List<TimedTask>>> t_token = new TypeToken<Map<TaskType, List<TimedTask>>>(){};
        Map<TaskType, List<Task>> _result1 = _gson.fromJson(_reader, t_token.getType());
        
        _reader = new FileReader(file_name);
        TypeToken<Map<TaskType, List<DeadlineTask>>> dd_token = new TypeToken<Map<TaskType, List<DeadlineTask>>>(){};
        Map<TaskType, List<Task>> _result2 = _gson.fromJson(_reader, dd_token.getType());
        
        _reader = new FileReader(file_name);
        TypeToken<Map<TaskType, List<FloatingTask>>> f_token = new TypeToken<Map<TaskType, List<FloatingTask>>>(){};
        Map<TaskType, List<Task>> _result3 = _gson.fromJson(_reader, f_token.getType());
        
        	_result.addAll(_result1.get(TaskType.TIMED));
        	_result.addAll(_result2.get(TaskType.DEADLINE));
        	_result.addAll(_result3.get(TaskType.FLOATING));

        
    } catch(IOException ex) {
        ex.printStackTrace();
        return null;
    }

    return _result;
}
}
