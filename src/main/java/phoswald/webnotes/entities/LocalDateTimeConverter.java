package phoswald.webnotes.entities;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

//@Converter // should be enabled when JPA 2.1 will work
public class LocalDateTimeConverter /* implements AttributeConverter<LocalDateTime, String> */ {

    private static final DateTimeFormatter FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    //@Override
    public String convertToDatabaseColumn(LocalDateTime attribute) {
        return attribute == null ? null : attribute.toString(FORMAT);
    }

    //@Override
    public LocalDateTime convertToEntityAttribute(String column) {
        return column == null ? null : LocalDateTime.parse(column, FORMAT);
    }
}
