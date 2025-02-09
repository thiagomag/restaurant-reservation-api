package br.com.postechfiap.restaurantreservationapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JsonUtils {

    private static final String LOG_PREFIX = "[JSON_UTILS]";

    private final ObjectMapper objectMapper;

    public <T> T readValueOrNull(String jsonBody, Class<T> clazz) {
        try {
            return objectMapper.readValue( jsonBody, clazz );
        } catch (JsonProcessingException e) {
            log.error( LOG_PREFIX + " " + e.getMessage(), e );
        }
        return null;
    }

    public <T> List<T> readValueOrNullForList(String jsonBody, Class<T> clazz) {
        try {
            final var objectReader = objectMapper.readerForListOf(clazz);
            return objectReader.readValue(jsonBody);
        } catch (JsonProcessingException e) {
            log.error( LOG_PREFIX + " " + e.getMessage(), e );
        }
        return null;
    }

    @SuppressWarnings("java:S112")
    public <T> T readValueOrThrow(String jsonBody, Class<T> clazz) {
        try {
            return objectMapper.readValue( jsonBody, clazz );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String writeValueAsStringOrNull(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error( LOG_PREFIX + " " + e.getMessage(), e );
        }
        return null;
    }

    @SuppressWarnings("java:S112")
    public String writeValueAsStringOrThrow(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
