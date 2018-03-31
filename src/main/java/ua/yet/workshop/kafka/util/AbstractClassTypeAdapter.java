package ua.yet.workshop.kafka.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class AbstractClassTypeAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    private static final String CLASS_META_KEY = "_CLAZZ";

    @Override
    public T deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObj = json.getAsJsonObject();
        final String className = jsonObj.get(CLASS_META_KEY).getAsString();
        try {
            return context.deserialize(json, Class.forName(className));
        } catch (final ClassNotFoundException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public JsonElement serialize(final T src, final Type typeOfSrc, final JsonSerializationContext context) {
        final JsonElement jsonElement = context.serialize(src, src.getClass());
        jsonElement.getAsJsonObject().addProperty(CLASS_META_KEY, src.getClass().getCanonicalName());
        return jsonElement;
    }
}
