//@author A0112918H
package com.epictodo.controller.json;

import com.epictodo.model.task.DeadlineTask;
import com.epictodo.model.task.FloatingTask;
import com.epictodo.model.task.Task;
import com.epictodo.model.task.TimedTask;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Storage {
    private static Logger _logger = Logger.getLogger("--- Storage Parser Log ---");
    public enum TaskType {
        FLOATING, DEADLINE, TIMED
    };


    /**
     * This method loads the Json file to ArrayList<Task> of memory objects
     * Construct them into MAP <TaskType, List<Task>>
     *
     * @param list
     * @return _result
     */
    public static ArrayList<Task> loadDbFile(String file_name) {
        File f = new File(file_name);
        Gson _gson = instantiateObject();
        ArrayList<Task> _result = makeArrayList(f, _gson);

        return _result;
    }
    
    
    /**
     * This method saves ArrayList<Task> from memory object to Json file.
     *
     * @param file_name		fileName
     * @param list			wholeList to be added
     * @return true
     */
    public static boolean saveToJson(String file_name, ArrayList<Task> list) {
        assert file_name.equalsIgnoreCase(file_name);
        _logger.log(Level.INFO, "Filename: \'storage.txt\' has been asserted.");

        Map<TaskType, List<Task>> _map = makeMap(list);

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
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * This method loads the Json file to ArrayList<Task> of memory objects
     * Construct them into MAP <TaskType, List<Task>>
     *
     * @param list
     * @return _result
     */
    private static Map<TaskType, List<Task>> makeMap(ArrayList<Task> list) {
        ArrayList<Task> timed_list = new ArrayList<>();
        ArrayList<Task> deadline_list = new ArrayList<>();
        ArrayList<Task> floating_list = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Task _task = list.get(i);
            if (_task instanceof TimedTask) {
                timed_list.add(_task);
            }
            if (_task instanceof DeadlineTask) {
                deadline_list.add(_task);
            }
            if (_task instanceof FloatingTask) {
                floating_list.add(_task);
            }
        }

        Map<TaskType, List<Task>> _map = new HashMap<>();
        _map.put(TaskType.TIMED, timed_list);
        _map.put(TaskType.DEADLINE, deadline_list);
        _map.put(TaskType.FLOATING, floating_list);
        return _map;
    }
    
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
     * This method make use of makeTask to retrieve all of tasks from the storage file
     * Returns an arraylist of Task from the storage
     *
     * @param file
     * @param _gson
     * @return _result
     */
    private static ArrayList<Task> makeArrayList(File file, Gson _gson) {
        ArrayList<Task> _result = new ArrayList<>();

        try {
            Map<TaskType, List<Task>> timed_result = makeTimedTaskList(file, _gson);
            Map<TaskType, List<Task>> deadline_result = makeDeadlineTaskList(file,
                    _gson);
            Map<TaskType, List<Task>> floatingt_result = makeFloatingTaskList(
                    file, _gson);

            _result.addAll(timed_result.get(TaskType.TIMED));
            _result.addAll(deadline_result.get(TaskType.DEADLINE));
            _result.addAll(floatingt_result.get(TaskType.FLOATING));
            return _result;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * This method reads the storage file and 
     * convert all of them into a Map consist of floating task
     *
     * @param file
     * @param _gson
     * @return Map of floating task
     */
    private static Map<TaskType, List<Task>> makeFloatingTaskList(File file, Gson _gson) throws FileNotFoundException {
        FileReader _reader;
        _reader = new FileReader(file);
        TypeToken<Map<TaskType, List<FloatingTask>>> f_token = new TypeToken<Map<TaskType, List<FloatingTask>>>() {
        };
        Map<TaskType, List<Task>> floatingt_result = _gson.fromJson(_reader, f_token.getType());
        return floatingt_result;
    }

    /**
     * This method reads the storage file and 
     * convert all of them into a Map consist of deadline task
     *
     * @param file
     * @param _gson
     * @return Map of deadline task
     */
    private static Map<TaskType, List<Task>> makeDeadlineTaskList(File f, Gson _gson) throws FileNotFoundException {
        FileReader _reader;
        _reader = new FileReader(f);
        TypeToken<Map<TaskType, List<DeadlineTask>>> dd_token = new TypeToken<Map<TaskType, List<DeadlineTask>>>() {
        };
        Map<TaskType, List<Task>> deadline_result = _gson.fromJson(_reader, dd_token.getType());
        return deadline_result;
    }

    /**
     * This method reads the storage file and 
     * convert all of them into a Map consist of timed task
     *
     * @param file
     * @param _gson
     * @return Map of timed task
     */
    private static Map<TaskType, List<Task>> makeTimedTaskList(File file, Gson _gson) throws FileNotFoundException {
        FileReader _reader = new FileReader(file);
        TypeToken<Map<TaskType, List<TimedTask>>> t_token = new TypeToken<Map<TaskType, List<TimedTask>>>() {
        };
        Map<TaskType, List<Task>> timed_result = _gson.fromJson(_reader, t_token.getType());
        return timed_result;
    }
}
