package com.redis.om.skeleton;
// import java.time.ZonedDateTime;

// import java.time.format.DateTimeFormatter;

// import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
// import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
// import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
// import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
// import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

// @Configuration
// public class JacksonConfiguration {

//     @Bean
//     public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

//         return builder -> {

//             // formatter
//             DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//             DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//             DateTimeFormatter zonedDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");

//             // deserializers
//             builder.deserializers(new LocalDateDeserializer(dateFormatter));
//             builder.deserializers(new InstantDeserializer<>(ZonedDateTime.class, ZonedDateTimeSerializer.INSTANCE,
//                     zonedDateTimeFormatter));

//             // serializers
//             builder.serializers(new LocalDateSerializer(dateFormatter));
//             builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
//             builder.serializers(new ZonedDateTimeSerializer(zonedDateTimeFormatter));
//         };
//     }
// }
import java.io.IOException;
import java.time.Instant;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {

            builder.deserializerByType(Instant.class, new JsonDeserializer<Instant>() {

                private final InstantDeserializer<Instant> delegate = InstantDeserializer.INSTANT;

                @Override
                public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                    return delegate.deserialize(p, ctxt);
                }
            });

            builder.serializerByType(Instant.class, InstantSerializer.INSTANCE);
        };
    }
}
