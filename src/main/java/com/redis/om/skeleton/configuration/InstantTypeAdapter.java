package com.redis.om.skeleton.configuration;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;

public class InstantTypeAdapter {

    public static final InstantDeserializer<Instant> delegate = InstantDeserializer.INSTANT;

    public static class Deserializer extends JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(com.fasterxml.jackson.core.JsonParser p, DeserializationContext ctxt)
                throws IOException {
            return Instant.from(InstantDeserializer.INSTANT.deserialize(p, ctxt));
        }
    }

    public static class Serializer extends JsonSerializer<Instant> {
        @Override
        public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.toString());
        }
    }
}
