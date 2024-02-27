package hu.racoonsoftware.filesystemchecker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

/**
 * This class represent the API error response.
 * @param traceId This is an auto generated id, for clearly identify the request in logs
 * @param timestamp Timestamp when the error occurred
 * @param status The HTTP status code which send to the client
 * @param errorMessage The error specific message
 */
public record ErrorDto(
        @Schema(description = "Identifier of request") String traceId,
        @Schema(description = "Time of request") OffsetDateTime timestamp,
        @Schema(description = "Http status") HttpStatus status,
        @Schema(description = "Error message", example = "Invalid path") String errorMessage) {
}
