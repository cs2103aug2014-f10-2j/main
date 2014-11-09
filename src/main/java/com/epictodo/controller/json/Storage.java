package com.epictodo.controller.json;

import java.io.File;
import java.io.FileNotFoundException;
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
    }

    ;

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
     * This method saves ArrayList<Task> from memory object to Json file.
     *
     * @param file_name
     * @param list
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
     * This method loads the Json file to ArrayList<Task> of memory objects
     *
     * @param file_name
     * @return _result
     */

    public static ArrayList<Task> loadDbFile(String file_name) {
        

        assert file_name.equalsIgnoreCase(file_name);
        _logger.log(Level.INFO, "Filename: \'storage.txt\' has been asserted.");
        File f = new File(file_name);
        Gson _gson = instantiateObject();
        ArrayList<Task> _result = makeArrayList( f, _gson);

        return _result;
    }

	private static ArrayList<Task> makeArrayList(File f,
			Gson _gson) {
		ArrayList<Task> _result = new ArrayList<>();
		try {
			Map<TaskType, List<Task>> timed_result = makeTimedTaskList(f, _gson);
            Map<TaskType, List<Task>> deadline_result = makeDeadlineTaskList(f,
					_gson);
            Map<TaskType, List<Task>> floatingt_result = makeFloatingTaskList(
					f, _gson);

            _result.addAll(timed_result.get(TaskType.TIMED));
            _result.addAll(deadline_result.get(TaskType.DEADLINE));
            _result.addAll(floatingt_result.get(TaskType.FLOATING));
            return _result;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
	}

	private static Map<TaskType, List<Task>> makeFloatingTaskList(File f,
			Gson _gson) throws FileNotFoundException {
		FileReader _reader;
		_reader = new FileReader(f);
		TypeToken<Map<TaskType, List<FloatingTask>>> f_token = new TypeToken<Map<TaskType, List<FloatingTask>>>() {
		};
		Map<TaskType, List<Task>> floatingt_result = _gson.fromJson(_reader, f_token.getType());
		return floatingt_result;
	}

	private static Map<TaskType, List<Task>> makeDeadlineTaskList(File f,
			Gson _gson) throws FileNotFoundException {
		FileReader _reader;
		_reader = new FileReader(f);
		TypeToken<Map<TaskType, List<DeadlineTask>>> dd_token = new TypeToken<Map<TaskType, List<DeadlineTask>>>() {
		};
		Map<TaskType, List<Task>> deadline_result = _gson.fromJson(_reader, dd_token.getType());
		return deadline_result;
	}

	private static Map<TaskType, List<Task>> makeTimedTaskList(File f,
			Gson _gson) throws FileNotFoundException {
		FileReader _reader = new FileReader(f); 
		TypeToken<Map<TaskType, List<TimedTask>>> t_token = new TypeToken<Map<TaskType, List<TimedTask>>>() {
		};
		Map<TaskType, List<Task>> timed_result = _gson.fromJson(_reader, t_token.getType());
		return timed_result;
	}
}
