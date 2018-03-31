package ua.yet.workshop.kafka.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

import static java.nio.charset.Charset.defaultCharset;

public class JsonSerde<T> implements Serde<T> {

    private final Class<T> clazz;

    private final Gson gson;

    public JsonSerde(final Class<T> clazz) {
        this(clazz, Collections.emptyMap());
    }

    public JsonSerde(final Class<T> clazz, final Map<Type, Object> typeAdapters) {
        this.clazz = clazz;

        final GsonBuilder builder = new GsonBuilder();
        typeAdapters.forEach(builder::registerTypeAdapter);
        this.gson = builder.create();
    }

    @Override
    public void configure(final Map<String, ?> configs, final boolean isKey) {
        // Doing nothing
    }

    @Override
    public void close() {
        // Doing nothing...
    }

    @Override
    public Serializer<T> serializer() {
        return new JsonSerializer();
    }

    @Override
    public Deserializer<T> deserializer() {
        return new JsonDeserializer();
    }

    private class JsonSerializer implements Serializer<T> {

        @Override
        public void configure(final Map<String, ?> configs, final boolean isKey) {
            // Doing nothing
        }

        @Override
        public byte[] serialize(final String topic, final T data) {
            return gson.toJson(data).getBytes(defaultCharset());
        }

        @Override
        public void close() {
            // Doing nothing
        }
    }

    private class JsonDeserializer implements Deserializer<T> {

        @Override
        public void configure(final Map<String, ?> configs, final boolean isKey) {
            // Doing nothing
        }

        @Override
        public T deserialize(final String topic, final byte[] data) {
            final String stringData = new String(data, defaultCharset());
            return gson.fromJson(stringData, clazz);
        }

        @Override
        public void close() {
            // Doing nothing
        }
    }
}
