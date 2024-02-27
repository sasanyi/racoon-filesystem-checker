package hu.racoonsoftware.filesystemchecker.repository;

import hu.racoonsoftware.filesystemchecker.config.AppConfiguration;
import hu.racoonsoftware.filesystemchecker.config.R2DBCConfig;
import hu.racoonsoftware.filesystemchecker.model.History;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@DataR2dbcTest
@Import({R2DBCConfig.class, AppConfiguration.class})
class HistoryRepositoryTest {

    private final HistoryRepository historyRepository;

    @Autowired
    HistoryRepositoryTest(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @BeforeEach
    void beforeEach(){
        this.historyRepository.deleteAll().block();
    }

    @Test
    void testSave() {
        StepVerifier.create(this.historyRepository.save(createNewTestHistory()))
                .assertNext(history -> {
                    Assertions.assertTrue(history.getResult().containsKey("asd.txt"));
                    Assertions.assertEquals(2, history.getResult().get("asd.txt"));
                })
                .expectComplete()
                .verify();
    }

    private History createNewTestHistory() {
        History history = new History(UUID.randomUUID(),
                "TEST",
                Map.ofEntries(Map.entry("asd.txt", 2)),
                LocalDateTime.now(),
                "container",
                null);
        history.setNew(true);
        return history;
    }

}