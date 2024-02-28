package hu.racoonsoftware.filesystemchecker.service;

import hu.racoonsoftware.filesystemchecker.dto.HistoryDto;
import hu.racoonsoftware.filesystemchecker.model.History;
import hu.racoonsoftware.filesystemchecker.repository.HistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class HistoryServiceImpTest {
    private final HistoryService historyService;

    @MockBean
    HistoryRepository historyRepository;

    @Autowired
    HistoryServiceImpTest(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Test
    void testFindAllHistory() {
        when(historyRepository.findAll()).thenReturn(Flux.fromIterable(getHistoryEntries()));
        Flux<HistoryDto> findHistories = this.historyService.findAllHistory();

        StepVerifier.create(findHistories)
                .expectNextCount(3)
                .verifyComplete();

        verify(historyRepository, times(1)).findAll();
    }

    @Test
    void testFindAllHistoryByValidName() {
        List<History> entries = getHistoryEntries().stream()
                .filter(history -> history.getRequestBy().equals("TEST"))
                .toList();

        when(historyRepository.findAllByRequestBy("TEST")).thenReturn(Flux.fromIterable(entries));

        Flux<HistoryDto> findHistories = this.historyService.findAllHistoryByName("TEST");

        StepVerifier.create(findHistories)
                .expectNextCount(2)
                .verifyComplete();

        StepVerifier.create(findHistories)
                .thenConsumeWhile(historyDto -> historyDto.requestBy().equals("TEST"))
                .verifyComplete();

        verify(historyRepository, times(1)).findAllByRequestBy("TEST");
    }

    @ParameterizedTest
    @ValueSource(strings = {"INVALID", ""})
    void testFindAllHistoryByInvalidOrEmptyName(String name) {
        List<History> entries = Collections.emptyList();

        when(historyRepository.findAllByRequestBy(name)).thenReturn(Flux.fromIterable(entries));

        Flux<HistoryDto> findHistories = this.historyService.findAllHistoryByName(name);

        StepVerifier.create(findHistories)
                .expectNextCount(0)
                .verifyComplete();

        verify(historyRepository, times(1)).findAllByRequestBy(name);
    }

    @Test
    void testSaveWithValidHistory() {
        History testHistory = createNewTestHistory();

        when(historyRepository.save(argThat(History::isNew))).thenReturn(Mono.just(testHistory));

        StepVerifier.create(this.historyService.saveHistory(testHistory))
                .assertNext(history -> {
                    assertTrue(history.result().containsKey("test.txt"));
                    assertEquals(2, history.result().get("test.txt"));
                })
                .expectComplete()
                .verify();

        verify(historyRepository, times(1)).save(testHistory);

    }

    private static History createNewTestHistory() {
        return new History(null,
                "TEST",
                Map.ofEntries(Map.entry("test.txt", 2)),
                LocalDateTime.now(),
                "testFileSystem",
                null);
    }
    private static List<History> getHistoryEntries() {
        return List.of(
                new History(
                        UUID.randomUUID(),
                        "TEST",
                        Map.ofEntries(
                                Map.entry("test.txt", 2)),
                        LocalDateTime.now(),
                        "testFileSystem",
                        "txt"
                ),
                new History(
                        UUID.randomUUID(),
                        "TEST",
                        Map.ofEntries(
                                Map.entry("test.txt", 2),
                                Map.entry("test.docx", 20)),
                        LocalDateTime.now(),
                        "testFileSystem",
                        "*"
                ),
                new History(
                        UUID.randomUUID(),
                        "TEST2",
                        Map.ofEntries(
                                Map.entry("test2.txt", 2)),
                        LocalDateTime.now(),
                        "testFileSystem",
                        "txt"
                )
        );
    }


}