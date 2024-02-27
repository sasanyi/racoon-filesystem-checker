package hu.racoonsoftware.filesystemchecker.service;

import hu.racoonsoftware.filesystemchecker.dto.HistoryDto;
import reactor.core.publisher.Mono;

/**
 * This interface define methods of FilesystemOperationService
 */
public interface FilesystemOperationService {
    /**
     * This method responsible for creating file catalog recursively from path
     * @param path The root path of operation
     * @param extension Optional parameter for specify the cataloged files extension
     * @return API response ({@link hu.racoonsoftware.filesystemchecker.dto.HistoryDto}) representation of history entry

     */
    Mono<HistoryDto> fileCatalog(String path, String extension);
}
