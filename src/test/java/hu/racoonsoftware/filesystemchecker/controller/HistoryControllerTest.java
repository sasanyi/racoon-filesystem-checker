package hu.racoonsoftware.filesystemchecker.controller;

import hu.racoonsoftware.filesystemchecker.dto.HistoryDto;
import hu.racoonsoftware.filesystemchecker.service.HistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@WebFluxTest(HistoryController.class)
@AutoConfigureWebTestClient
class HistoryControllerTest {
    private final WebTestClient webTestClient;

    @MockBean
    private HistoryService historyService;

    @Autowired
    public HistoryControllerTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }


    @Test
    void testGetHistoryWithoutNameFilter() {
        List<HistoryDto> historyEntries = getTestHistoryDtoList();

        when(historyService.findAllHistory()).thenReturn(Flux.fromIterable(historyEntries));

        webTestClient.get()
                .uri(HistoryController.HISTORY_BASE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.size()").isEqualTo(historyEntries.size());

        verify(historyService, times(1)).findAllHistory();
    }

    @Test
    void testGetHistoryWithNameParam() {
        List<HistoryDto> historyEntries = getTestHistoryDtoList().stream().filter(historyDto ->
                historyDto.requestBy().equals("TEST2")).toList();

        when(historyService.findAllHistoryByName("TEST2")).thenReturn(Flux.fromIterable(historyEntries));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(HistoryController.HISTORY_BASE_PATH)
                        .queryParam("name", "TEST2")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.size()").isEqualTo(historyEntries.size());

        verify(historyService, times(1)).findAllHistoryByName("TEST2");
    }

    @Test
    void testGetHistoryWithEmptyNameParam() {
        List<HistoryDto> historyEntries = getTestHistoryDtoList();

        when(historyService.findAllHistory()).thenReturn(Flux.fromIterable(historyEntries));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(HistoryController.HISTORY_BASE_PATH)
                        .queryParam("name", "")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.size()").isEqualTo(historyEntries.size());

        verify(historyService, times(1)).findAllHistory();
    }

    @Test
    void testGetHistoryWithInvalidNameParameter() {
        List<HistoryDto> historyEntries = Collections.emptyList();

        when(historyService.findAllHistoryByName("INVALID")).thenReturn(Flux.fromIterable(historyEntries));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(HistoryController.HISTORY_BASE_PATH)
                        .queryParam("name", "INVALID")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.size()").isEqualTo(0);

        verify(historyService, times(1)).findAllHistoryByName("INVALID");
    }

    private static List<HistoryDto> getTestHistoryDtoList() {
        return List.of(
                new HistoryDto(
                        "TEST",
                        Map.ofEntries(Map.entry("test.txt", 2)),
                        LocalDateTime.now(),
                        "testFileSystem",
                        null),
                new HistoryDto(
                        "TEST2",
                        Map.ofEntries(Map.entry("test2.txt", 4)),
                        LocalDateTime.now(),
                        "testFileSystem",
                        null),
                new HistoryDto(
                        "TEST2",
                        Map.ofEntries(Map.entry("test2.txt", 1)),
                        LocalDateTime.now(),
                        "testFileSystem",
                        null)
        );
    }
}