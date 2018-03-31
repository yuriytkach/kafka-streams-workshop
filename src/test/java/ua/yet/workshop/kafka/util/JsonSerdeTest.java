package ua.yet.workshop.kafka.util;

import com.google.gson.reflect.TypeToken;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.kafka.streams.kstream.Window;
import org.apache.kafka.streams.kstream.internals.TimeWindow;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashMap;

import static java.nio.charset.Charset.defaultCharset;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"checkstyle:FinalLocalVariable", "checkstyle:MagicNumber", "PMD.LocalVariableCouldBeFinal"})
public class JsonSerdeTest {

    @Test
    @SneakyThrows
    public void correctSerializeDeserializeSimpleClass() {
        // Given serde and value
        val serde = new JsonSerde<Simple>(Simple.class);

        val value = new Simple("aaa", 5, LocalDateTime.of(2018, 1, 15, 22, 34));

        // When serializing
        val data = new String(serde.serializer().serialize("t", value), defaultCharset());

        // Then json should be as expected
        JSONAssert.assertEquals("{\"a\":\"aaa\",\"b\":5,"
                + "\"d\":{\"date\":{\"year\":2018,\"month\":1,\"day\":15},"
                + "\"time\":{\"hour\":22,\"minute\":34,\"second\":0,\"nano\":0}}}", data, true);

        // When deserializing
        val simple = serde.deserializer().deserialize("t", data.getBytes(defaultCharset()));

        // Then object should be as initial value
        assertThat(simple).isEqualTo(value);
    }

    @Test
    @SneakyThrows
    @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
    public void correctSerializeDeserializeClassWithAbstracts() {
        // Given map for abstract class type adapter
        val adapter = new AbstractClassTypeAdapter<Window>();

        val typeToken = new TypeToken<Window>() { };

        val typeAdaptersMap = new HashMap<Type, Object>();
        typeAdaptersMap.put(typeToken.getType(), adapter);

        // Given serde with type adapters and value
        val serde = new JsonSerde<WithAbstracts>(WithAbstracts.class, typeAdaptersMap);

        val value = new WithAbstracts("aaa", 5, new TimeWindow(1000, 2000));

        // When serializing
        val data = new String(serde.serializer().serialize("t", value), defaultCharset());

        // Then json should be as expected
        JSONAssert.assertEquals("{\"a\":\"aaa\",\"b\":5,"
                + "\"c\":{\"startMs\":1000,\"endMs\":2000,"
                + "\"_CLAZZ\":\"org.apache.kafka.streams.kstream.internals.TimeWindow\"}}", data, true);

        // When deserializing
        val simple = serde.deserializer().deserialize("t", data.getBytes(defaultCharset()));

        // Then object should be as initial value
        assertThat(simple).isEqualTo(value);
    }

    @Data
    private static class Simple {
        private final String a;
        private final int b;
        private final LocalDateTime d;
    }

    @Data
    private static class WithAbstracts {
        private final String a;
        private final int b;
        private final Window c;
    }

}