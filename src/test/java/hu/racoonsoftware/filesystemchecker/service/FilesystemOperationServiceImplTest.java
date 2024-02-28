package hu.racoonsoftware.filesystemchecker.service;

import hu.racoonsoftware.filesystemchecker.dto.HistoryDto;
import hu.racoonsoftware.filesystemchecker.exception.PathNotFoundException;
import hu.racoonsoftware.filesystemchecker.model.History;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class FilesystemOperationServiceImplTest {

    private final FilesystemOperationService filesystemOperationService;
    @MockBean
    HistoryService historyService;

    @Autowired
    FilesystemOperationServiceImplTest(FilesystemOperationService filesystemOperationService) {
        this.filesystemOperationService = filesystemOperationService;
    }


    @BeforeEach
    void setUp() {

        when(historyService.saveHistory(any(History.class))).then(i -> {
            History param = i.getArgument(0, History.class);
            return Mono.just(new HistoryDto(
                    param.getRequestBy(),
                    param.getResult(),
                    param.getRequestedOn(),
                    param.getPath(),
                    param.getExtension()
            ));
        });

    }

    @Test
    void testFileCatalogWithCorrectPathAndWithoutExtension() {
        StepVerifier.create(this.filesystemOperationService.fileCatalog("testFileSystem", null))
                .assertNext(historyDto -> {
                    Assertions.assertTrue(historyDto.result().containsKey("asd.txt"));
                    Assertions.assertEquals(2, historyDto.result().get("asd.txt"));
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testFileCatalogWithCorrectPathAndWithExtension() {
        StepVerifier.create(this.filesystemOperationService.fileCatalog("testFileSystem", "csv"))
                .assertNext(historyDto -> {
                    Assertions.assertTrue(historyDto.result().containsKey("asd.csv"));
                    Assertions.assertFalse(historyDto.result().containsKey("asd.tyt"));
                    Assertions.assertEquals(1, historyDto.result().get("asd.csv"));
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testFileCatalogWithInvalidPath() {
        StepVerifier.create(this.filesystemOperationService.fileCatalog("INVALID", null))
                .expectErrorMatches(throwable ->
                        throwable instanceof PathNotFoundException && throwable.getMessage().equals("Invalid path"))
                .verify();
    }
}