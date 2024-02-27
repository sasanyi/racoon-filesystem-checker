package hu.racoonsoftware.filesystemchecker.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * This class represent an operation history
 * @param requestBy Define who was the requester of operation
 * @param result Define the result of operation
 * @param requestedOn Define when was request
 * @param path Define which path was the root path of operation
 * @param extension Optional parameter for extension filter operation. Define the filtered extension
 */
public record HistoryDto(String requestBy,
                         @Schema(description = "Result of catalog request." +
                                 " Contains files and number of occure",
                                 example = "{\"1.txt\" : 2}")
                         Map<String, Integer> result,
                         @Schema(description = "Time of request") LocalDateTime requestedOn,
                         @Schema(description = "Root directory of search") String path,
                         @Schema(description = "Extension of searched files") String extension) {
}
