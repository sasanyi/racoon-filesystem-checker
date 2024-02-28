package hu.racoonsoftware.filesystemchecker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * This class responsible for base bean definitions
 */
@Configuration
public class AppConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(dateTimeFormatter);

        javaTimeModule.addSerializer(LocalDateTime.class, localDateTimeSerializer);

        objectMapper.registerModule(javaTimeModule);

        return objectMapper;
    }

}
