package hu.racoonsoftware.filesystemchecker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.racoonsoftware.filesystemchecker.config.support.JSONToMapConverter;
import hu.racoonsoftware.filesystemchecker.config.support.MapToJSONConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.util.ArrayList;
import java.util.List;

/**
 * This class responsible for R2DBC configuration
 */
@Configuration
@EnableR2dbcRepositories
public class R2DBCConfig {
    private final ObjectMapper objectMapper;

    public R2DBCConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * In this method can register specific type conversion between database column types and Java types
     * @return R2DBC converters
     */
    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new JSONToMapConverter<>(objectMapper));
        converters.add(new MapToJSONConverter<>(objectMapper));
        return R2dbcCustomConversions.of(PostgresDialect.INSTANCE, converters);
    }

}
