package hu.racoonsoftware.filesystemchecker.service;

import hu.racoonsoftware.filesystemchecker.dto.HistoryDto;
import hu.racoonsoftware.filesystemchecker.exception.PathNotFoundException;
import hu.racoonsoftware.filesystemchecker.model.History;
import hu.racoonsoftware.filesystemchecker.util.filesystem.FilesystemHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * This class implementation of {@link hu.racoonsoftware.filesystemchecker.service.FilesystemOperationService}.
 * This implementation uses Database for store operation history entry and use own implementation of catalog algorithm
 * in {@link hu.racoonsoftware.filesystemchecker.util.filesystem.FilesystemHelper}
 */
@Service
public class FilesystemOperationServiceImpl implements FilesystemOperationService {

    private final HistoryService historyService;

    public FilesystemOperationServiceImpl(HistoryService historyService) {
        this.historyService = historyService;
    }

    /**
     * @param path      The root path of operation
     * @param extension Optional parameter for specify the cataloged files extension
     * @return API response ({@link hu.racoonsoftware.filesystemchecker.dto.HistoryDto}) representation of history entry
     */
    @Override
    public Mono<HistoryDto> fileCatalog(String path, String extension) {
        return Mono.fromCallable(() -> FilesystemHelper.catalogFilesRecursivelyInAFolder(path, extension))
                .onErrorMap(IOException.class,
                        e -> new PathNotFoundException()).map(result -> createHistoryEntity(result, path, extension))
                .flatMap(this::saveHistory);
    }

    private History createHistoryEntity(Map<String, Integer> catalog, String path, String extension) {
        return new History(
                UUID.randomUUID(),
                System.getenv("APP_USER"),
                catalog,
                LocalDateTime.now(),
                path,
                (extension != null && !extension.isEmpty()) ? extension : null);
    }

    private Mono<HistoryDto> saveHistory(History history) {
        return this.historyService.saveHistory(history);
    }
}
