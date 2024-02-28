package hu.racoonsoftware.filesystemchecker.config.support;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for converting the JSON type columns of database
 * to Java Map type objects
 *
 * @param <K> Map key type
 * @param <V> Map value type
 */
@ReadingConverter
public class JSONToMapConverter<K, V> implements Converter<String, Map<K, V>> {

    private final ObjectMapper objectMapper;
    private static final Logger LOG = LogManager.getLogger(JSONToMapConverter.class);

    public JSONToMapConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<K, V> convert(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (IOException e) {
            LOG.error("Problem while parsing JSON: {}", json, e);
        }
        return new HashMap<>();
    }

}