package phoswald.webnotes.rest;

import java.io.IOException;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Provider
public class JacksonContextResolver implements ContextResolver<ObjectMapper> {

    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"); // was: "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    private final ObjectMapper mapper;

    public JacksonContextResolver() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        mapper = new ObjectMapper();
        mapper.registerModule(module);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }

    private static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime date, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
            try {
                generator.writeString(date == null ? "" : date.toString(DATETIME_FORMAT));
            } catch (IllegalArgumentException ex) { // should be DateTimeParseException for Java 8
                throw new IOException(ex);
            }
        }
    }

    private static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
            try {
                String text = parser.getText();
                return text == null || text.length() == 0 ? null : LocalDateTime.parse(text, DATETIME_FORMAT);
            } catch (IllegalArgumentException ex) { // should be DateTimeParseException for Java 8
                throw new IOException(ex);
            }
        }
    }
}
