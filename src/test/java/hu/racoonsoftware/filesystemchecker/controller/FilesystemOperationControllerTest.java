package hu.racoonsoftware.filesystemchecker.controller;

import hu.racoonsoftware.filesystemchecker.dto.ErrorDto;
import hu.racoonsoftware.filesystemchecker.dto.HistoryDto;
import hu.racoonsoftware.filesystemchecker.exception.PathNotFoundException;
import hu.racoonsoftware.filesystemchecker.service.FilesystemOperationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@WebFluxTest(FilesystemOperationController.class)
@AutoConfigureWebTestClient
class FilesystemOperationControllerTest {

    private final WebTestClient webTestClient;

    @MockBean
    private FilesystemOperationService filesystemOperationService;

    @Autowired
    FilesystemOperationControllerTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @Test
    void testFileCatalogWithValidPathAndWithoutExtension() {

        String path = "testFileSystem";

        HistoryDto historyDto = getTestHistoryDto(getTestCatalog(), path, null);
        when(filesystemOperationService.fileCatalog(path, null)).thenReturn(Mono.just(historyDto));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(FilesystemOperationController.FILESYSTEM_OPERATION_FILE_CATALOG_PATH)
                        .queryParam("path", path)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(HistoryDto.class)
                .isEqualTo(historyDto);

        verify(filesystemOperationService, times(1)).fileCatalog(path, null);
    }

    @Test
    void testFileCatalogWithValidPathAndWithExtension() {

        String path = "testFileSystem";
        String extension = "txt";

        Map<String, Integer> catalog = getTestCatalog().entrySet().stream()
                .filter(e -> e.getKey().endsWith(extension))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        HistoryDto historyDto = getTestHistoryDto(catalog, path, extension);
        when(filesystemOperationService.fileCatalog(path, extension)).thenReturn(Mono.just(historyDto));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(FilesystemOperationController.FILESYSTEM_OPERATION_FILE_CATALOG_PATH)
                        .queryParam("path", path)
                        .queryParam("extension", extension)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(HistoryDto.class)
                .isEqualTo(historyDto);

        verify(filesystemOperationService, times(1)).fileCatalog(path, extension);
    }

    @Test
    void testFileCatalogWithValidPathAndWithEmptyExtension() {

        String path = "testFileSystem";
        String extension = "";

        HistoryDto historyDto = getTestHistoryDto(getTestCatalog(), path, extension);

        when(filesystemOperationService.fileCatalog(path, extension)).thenReturn(Mono.just(historyDto));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(FilesystemOperationController.FILESYSTEM_OPERATION_FILE_CATALOG_PATH)
                        .queryParam("path", path)
                        .queryParam("extension", extension)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(HistoryDto.class)
                .isEqualTo(historyDto);

        verify(filesystemOperationService, times(1)).fileCatalog(path, extension);
    }

    @Test
    void testFileCatalogWithInvalidPath() {

        String path = "INVALID_PATH";

        when(filesystemOperationService.fileCatalog(path, null)).thenThrow(new PathNotFoundException());

        ErrorDto result = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(FilesystemOperationController.FILESYSTEM_OPERATION_FILE_CATALOG_PATH)
                        .queryParam("path", path)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorDto.class)
                .returnResult().getResponseBody();

        assertNotNull(result);
        assertEquals("Invalid path", result.errorMessage());
        assertEquals(HttpStatus.BAD_REQUEST, result.status());

        verify(filesystemOperationService, times(1)).fileCatalog(path, null);
    }

    private static HistoryDto getTestHistoryDto(Map<String, Integer> catalog, String path, String extension) {
        return new HistoryDto("TEST_USER", catalog, LocalDateTime.now(), path, extension);
    }
    private static Map<String, Integer> getTestCatalog() {
        return Map.ofEntries(
                Map.entry("file1.txt", 10),
                Map.entry("file2.docx", 100),
                Map.entry("file3.txt", 1));
    }

}