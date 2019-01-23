package ua.yet.workshop.kafka;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class KafkaStreamsSimpleApp {

    public static void main(String[] args) {
        Properties config = new Properties();
        // TODO configure application ID and broker location

        StreamsBuilder builder = new StreamsBuilder();
        // TODO build topology

        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                streams.close(10, TimeUnit.SECONDS))
        );
    }

}
