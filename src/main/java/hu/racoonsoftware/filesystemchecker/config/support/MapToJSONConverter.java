package hu.racoonsoftware.filesystemchecker.config.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.util.Map;

/**
 * This class is responsible for converting the Java Map objects to database JSONB type
 *
 * @param <K> Map key type
 * @param <V> Map value type
 */
@WritingConverter
public class MapToJSONConverter<K, V> implements Converter<Map<K, V>, String> {
    private static final Logger LOG = LogManager.getLogger(MapToJSONConverter.class);
    private final ObjectMapper objectMapper;

    public MapToJSONConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String convert(Map<K, V> source) {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            LOG.error("Error occurred while serializing map to JSON: {}", source, e);
        }
        return "";
    }
}
