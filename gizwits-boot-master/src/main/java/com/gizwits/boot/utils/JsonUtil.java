package com.gizwits.boot.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by rongmc on 16/5/31.
 */
public class JsonUtil {


    /**
     * 统一将data数据转换成JsonObject
     * @param dataStr
     * @return
     */
    public static JsonObject getJsonObject(String dataStr){
        if(ParamUtil.isNullOrEmptyOrZero(dataStr)){
            return null ;
        }
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(dataStr).getAsJsonObject();
        return jsonObject;
    }

    public static String getString(JsonObject data, String key) {
        if(key == null) {
            return null;
        } else if(data != null && data.has(key)) {
            JsonElement value = data.get(key);
            return value.isJsonNull()?null:value.getAsString().trim();
        } else {
            return null;
        }
    }

    public static  <T> T getObj(JsonObject data, String key, Class<T> t) {
        if(key == null) {
            return null;
        } else {
            if(data != null && data.has(key)) {
                JsonElement value = data.get(key);
                if(!value.isJsonNull()) {
                    Gson gson = new Gson();
                    return gson.fromJson(value.getAsString(), t);
                }
            }

            return null;
        }
    }

    public static List getList(JsonObject data, String key, Type type) {
        if(key == null) {
            return null;
        } else {
            if(data != null && data.has(key)) {
                JsonElement value = data.get(key);
                if(!value.isJsonNull()) {
                    Gson gson = new Gson();
                    return (List)gson.fromJson(value, type);
                }
            }

            return null;
        }
    }

    public static Map getMap(JsonObject data, String key, Type type) {
        if(key == null) {
            return null;
        } else {
            if(data != null && data.has(key)) {
                JsonElement value = data.get(key);
                if(!value.isJsonNull()) {
                    Gson gson = new Gson();
                    return (Map)gson.fromJson(value, type);
                }
            }

            return null;
        }
    }

    public static  <T> T getEnum(JsonObject data, String key, Class<T> t) {
        if(key == null) {
            return null;
        } else {
            if(data != null && data.has(key)) {
                JsonElement value = data.get(key);
                if(value.isJsonPrimitive()) {
                    Gson gson = new Gson();
                    return gson.fromJson(value.getAsString(), t);
                }
            }

            return null;
        }
    }

    public static Long getLong(JsonObject data, String key) {
        if(key == null) {
            return null;
        } else if(data != null && data.has(key)) {
            JsonElement value = data.get(key);
            return value.isJsonNull()?null: Long.valueOf(value.getAsLong());
        } else {
            return null;
        }
    }

    public static Integer getInteger(JsonObject data, String key) {
        if(key == null) {
            return null;
        } else if(data != null && data.has(key)) {
            JsonElement value = data.get(key);
            return value.isJsonNull()?null: Integer.valueOf(value.getAsInt());
        } else {
            return null;
        }
    }

    public static Short getShort(JsonObject data, String key) {
        if(key == null) {
            return null;
        } else if(data != null && data.has(key)) {
            JsonElement value = data.get(key);
            return value.isJsonNull()?null: Short.valueOf(value.getAsShort());
        } else {
            return null;
        }
    }

    public static Double getDouble(JsonObject data, String key) {
        if(key == null) {
            return null;
        } else if(data != null && data.has(key)) {
            JsonElement value = data.get(key);
            return value.isJsonNull()?null: Double.valueOf(value.getAsDouble());
        } else {
            return null;
        }
    }

    public static Boolean getBoolean(JsonObject data, String key) {
        if(key == null) {
            return null;
        } else if(data != null && data.has(key)) {
            JsonElement value = data.get(key);
            return value.isJsonNull()?null: Boolean.valueOf(value.getAsBoolean());
        } else {
            return null;
        }
    }

    public static Boolean getBoolean(JsonObject data, String key, Boolean dValue) {
        Boolean value = getBoolean(data,key);
        return null == value?dValue:value;
    }
}
