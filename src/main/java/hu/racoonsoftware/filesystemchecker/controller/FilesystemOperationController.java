package hu.racoonsoftware.filesystemchecker.controller;

import hu.racoonsoftware.filesystemchecker.dto.HistoryDto;
import hu.racoonsoftware.filesystemchecker.service.FilesystemOperationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * This class define all RESAT API routs for /api/operation. These REST endpoints documented in swagger ui
 */
@RestController
@RequestMapping("/api/operation")
public class FilesystemOperationController {

    private final FilesystemOperationService filesystemOperationService;

    public FilesystemOperationController(FilesystemOperationService filesystemOperationService) {
        this.filesystemOperationService = filesystemOperationService;
    }

    /**
     * @param path Define the root path of search
     * @param extension Optional parameter for filtering specific file extension
     * @return File catalog wrapped in the history object saved to database
     */
    @Operation(
            summary = "Create catalog",
            description = "This endpoint is used to create catalog of files in a folder represented by path"
    )
    @GetMapping("/fileCatalog")
    public Mono<HistoryDto> fileCatalog(@RequestParam(value = "path") String path,
                                        @RequestParam(value = "extension", required = false) String extension) {
        return filesystemOperationService.fileCatalog(path, extension);

    }
}
