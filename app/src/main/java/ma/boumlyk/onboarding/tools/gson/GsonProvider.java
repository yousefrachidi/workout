package ma.boumlyk.onboarding.tools.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class GsonProvider {

    public static Gson gson;
    public static Gson notNullsGson;

    public static ExclusionStrategy strategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            return field.getAnnotation(Exclude.class) != null;
        }
    };


    public static Gson getInstance() {
        if (gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSSS");
            gsonBuilder.addSerializationExclusionStrategy(strategy);
//            gsonBuilder.registerTypeAdapter(NotificationReference.class, new NotificationReferenceSerializerDeserializer());
            gsonBuilder.serializeNulls();
            gsonBuilder.setLenient();
            gson = gsonBuilder.create();
        }
        return gson;
    }

    public static Gson getNotNullsInstance() {
        if (notNullsGson == null) {
            GsonBuilder gsonBuildr = new GsonBuilder();
            gsonBuildr.addSerializationExclusionStrategy(strategy);
//            gsonBuildr.registerTypeAdapter(NotificationReference.class, new NotificationReferenceSerializerDeserializer());
            notNullsGson = gsonBuildr.create();
        }
        return notNullsGson;
    }

}


/*class NotificationReferenceSerializerDeserializer implements JsonSerializer<NotificationReference>, JsonDeserializer<NotificationReference> {
    @Override
    public JsonElement serialize(NotificationReference src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getId());
    }

    @Override
    public NotificationReference deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return NotificationReference.getEnumById(json.getAsString());
    }
}*/







