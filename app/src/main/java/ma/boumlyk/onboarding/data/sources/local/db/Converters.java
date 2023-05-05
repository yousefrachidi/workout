package ma.boumlyk.onboarding.data.sources.local.db;

import androidx.room.TypeConverter;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ma.boumlyk.onboarding.tools.gson.GsonProvider;

public class Converters {
    @TypeConverter
    public static Set<Long> stringToSetLong(String value) {
        return GsonProvider.getInstance().fromJson(value, new TypeToken<Set<Long>>() {
        }.getType());
    }

    @TypeConverter
    public static String setLongToString(Set<Long> value) {
        return GsonProvider.getInstance().toJson(value);
    }

    @TypeConverter
    public static Map<String, Integer> stringToMapStringInteger(String value) {
        return GsonProvider.getInstance().fromJson(value, new TypeToken<Map<String, Integer>>() {
        }.getType());
    }

    @TypeConverter
    public static String mapStringIntegerToString(Map<String, Integer> value) {
        return GsonProvider.getInstance().toJson(value);
    }


    @TypeConverter
    public static Map<Integer, String> stringToMapIntegerString(String value) {
        return GsonProvider.getInstance().fromJson(value, new TypeToken<Map<Integer, String>>() {
        }.getType());
    }

    @TypeConverter
    public static String mapIntegerStringToString(Map<Integer, String> value) {
        return GsonProvider.getInstance().toJson(value);
    }


    @TypeConverter
    public static Map<String, String> stringToMap(String value) {
        return GsonProvider.getInstance().fromJson(value, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    @TypeConverter
    public static String mapToString(Map<String, String> value) {
        return GsonProvider.getInstance().toJson(value);
    }

    @TypeConverter
    public static Set<String> stringToSet(String value) {
        return GsonProvider.getInstance().fromJson(value, new TypeToken<Set<String>>() {
        }.getType());
    }

    @TypeConverter
    public static String setToString(Set<String> value) {
        return GsonProvider.getInstance().toJson(value);
    }

    @TypeConverter
    public static List<String> stringToStringList(String value) {
        return GsonProvider.getInstance().fromJson(value, new TypeToken<List<String>>() {
        }.getType());
    }

    @TypeConverter
    public static String StringListToString(List<String> value) {
        return GsonProvider.getInstance().toJson(value);
    }

    @TypeConverter
    public static List<Long> stringToLongList(String value) {
        return GsonProvider.getInstance().fromJson(value, new TypeToken<List<Long>>() {
        }.getType());
    }

    @TypeConverter
    public static String longListToString(List<Long> value) {
        return GsonProvider.getInstance().toJson(value);
    }

    @TypeConverter
    public static JsonObject stringToJsonObject(String value) {

        if (value == null)
            return new JsonObject();
        return GsonProvider.getInstance().fromJson(value, JsonObject.class);
    }

    @TypeConverter
    public static String jsonObjectToString(JsonObject value) {
        if (value == null)
            value = new JsonObject();
        return GsonProvider.getInstance().toJson(value);
    }

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


}
