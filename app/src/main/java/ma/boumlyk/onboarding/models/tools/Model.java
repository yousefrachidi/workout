package ma.boumlyk.onboarding.models.tools;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ma.boumlyk.onboarding.tools.gson.GsonProvider;

public class Model {

    public <T> Map<String, String> toFlattenMap(T x, List<String> embedded_attributes, List<String> ignored_attributes) {
        Map<String, String> parameters = new HashMap<>();

        Gson gson = GsonProvider.getInstance();
        JsonObject json = gson.toJsonTree(x).getAsJsonObject();
        for (String value : embedded_attributes)
            parameters.putAll(jsonObjectToMapOfStrings(gson, json.remove(value).getAsJsonObject()));
        for (String value : ignored_attributes)
            json.remove(value);
        parameters.putAll(jsonObjectToMapOfStrings(gson, json));
        return parameters;
    }


    public Map<String, String> jsonObjectToMapOfStrings(Gson gson, JsonObject json) {

        Map<String, String> result = new HashMap<>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            Log.d("entry.getValue()", entry.getValue().toString());
            if (entry.getValue().isJsonPrimitive())
                result.put(entry.getKey(), entry.getValue().getAsString());
            else if (entry.getValue().isJsonNull())
                result.put(entry.getKey(), "");
            else
                result.put(entry.getKey(), gson.toJson(entry.getValue()));
        }
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        Gson gson = GsonProvider.getInstance();
        return gson.toJson(this);
    }

    public String getInstanceId() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

}
