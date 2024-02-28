package hu.racoonsoftware.filesystemchecker.controller;

import hu.racoonsoftware.filesystemchecker.dto.ErrorDto;
import hu.racoonsoftware.filesystemchecker.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * This class define all global exception handler
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * @param ex The exception object
     * @return Representation of error wrapped in ResponseEntity
     */
    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createErrorDto(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    private ErrorDto createErrorDto(HttpStatus status, String message) {
        return new ErrorDto(UUID.randomUUID().toString(), OffsetDateTime.now(), status, message);
    }
}
