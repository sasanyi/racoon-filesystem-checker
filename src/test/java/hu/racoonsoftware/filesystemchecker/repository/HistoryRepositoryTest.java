package hu.racoonsoftware.filesystemchecker.repository;

import hu.racoonsoftware.filesystemchecker.config.AppConfiguration;
import hu.racoonsoftware.filesystemchecker.config.R2DBCConfig;
import hu.racoonsoftware.filesystemchecker.model.History;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
@Import({R2DBCConfig.class, AppConfiguration.class})
class HistoryRepositoryTest {

    private final HistoryRepository historyRepository;

    @Autowired
    HistoryRepositoryTest(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @BeforeEach
    void beforeEach() {
        this.historyRepository.deleteAll().block();
        this.historyRepository.saveAll(initHistoryDatabase()).blockLast();
    }

    @Test
    void testSaveWithValidHistory() {
        History testHistory = createNewTestHistory(null);

        StepVerifier.create(this.historyRepository.save(testHistory))
                .assertNext(history -> {
                    assertTrue(history.getResult().containsKey("test.txt"));
                    assertEquals(2, history.getResult().get("test.txt"));
                })
                .expectComplete()
                .verify();

        assert testHistory.getId() != null;

        StepVerifier.create(this.historyRepository.findById(testHistory.getId()))
                .assertNext(savedHistory -> {
                    assertNotNull(savedHistory);
                    assertEquals(testHistory.getId(), savedHistory.getId());
                    assertEquals(testHistory.getRequestBy(), savedHistory.getRequestBy());
                    assertEquals(testHistory.getResult(), savedHistory.getResult());
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testSaveWithInValidHistory() {
        History testHistory = createUpdateHistory(null);

        StepVerifier.create(this.historyRepository.save(testHistory))
                .expectError()
                .verify();
    }

    @Test
    void testUpdateWithInValidHistory() {
        History testHistory = this.historyRepository.save(createNewTestHistory(null)).block();

        assertNotNull(testHistory);

        testHistory.setNew(false);
        testHistory.setRequestBy("TEST2");

        StepVerifier.create(this.historyRepository.save(testHistory))
                .assertNext(savedHistory -> {
                    assertNotNull(savedHistory);
                    assertEquals(testHistory.getId(), savedHistory.getId());
                    assertEquals("TEST2", savedHistory.getRequestBy());
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testFindAllByRequestBy() {

        Flux<History> findHistories = this.historyRepository.findAllByRequestBy("TEST");

        StepVerifier.create(findHistories)
                .expectNextCount(2)
                .verifyComplete();

        StepVerifier.create(findHistories)
                .thenConsumeWhile(history -> history.getRequestBy().equals("TEST"))
                .verifyComplete();
    }

    private static History createNewTestHistory(String extension) {
        History history = new History(UUID.randomUUID(),
                "TEST",
                Map.ofEntries(Map.entry("test.txt", 2)),
                LocalDateTime.now(),
                "testFileSystem",
                extension);
        history.setNew(true);
        return history;
    }

    private static History createNewTestHistory(String requestBy, Map<String, Integer> result, String path, String extension) {
        History history = new History(UUID.randomUUID(),
                requestBy,
                result,
                LocalDateTime.now(),
                path,
                extension);
        history.setNew(true);
        return history;
    }

    private static History createUpdateHistory(String extension) {
        return new History(UUID.randomUUID(),
                "TEST",
                Map.ofEntries(Map.entry("test.txt", 2)),
                LocalDateTime.now(),
                "testFileSystem",
                extension);
    }

    private static List<History> initHistoryDatabase() {
        return List.of(
                createNewTestHistory(
                        "TEST",
                        Map.ofEntries(
                                Map.entry("test.txt", 2)),
                        "testFileSystem",
                        "txt"
                ),
                createNewTestHistory(
                        "TEST",
                        Map.ofEntries(
                                Map.entry("testB.txt", 100),
                                Map.entry("testB.docx", 20)),
                        "testFileSystem",
                        null
                ),
                createNewTestHistory(
                        "TEST2",
                        Map.ofEntries(
                                Map.entry("test2.txt", 2)),
                        "testFileSystem",
                        "txt"
                )
        );
    }

}