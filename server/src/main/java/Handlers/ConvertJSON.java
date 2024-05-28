package Handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConvertJSON {

    public static <T> T fromJSON(String json, Class<T> class1) {
        Gson gson = new Gson();
        T newObj = gson.fromJson(json, class1);
        return newObj;
    }

    public static String toJSON(Object obj) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(obj);
        return jsonString;
    }
}
