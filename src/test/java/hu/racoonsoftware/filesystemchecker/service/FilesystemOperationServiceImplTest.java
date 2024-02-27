package hu.racoonsoftware.filesystemchecker.service;

import hu.racoonsoftware.filesystemchecker.dto.HistoryDto;
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

        Mockito.when(historyService.saveHistory(any(History.class))).then(i -> {
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

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFileCatalogWithCorrectPathAndWithoutExtension() {
        StepVerifier.create(this.filesystemOperationService.fileCatalog("container", null))
                .assertNext(historyDto -> {
                    Assertions.assertTrue(historyDto.result().containsKey("asd.txt"));
                    Assertions.assertEquals(2, historyDto.result().get("asd.txt"));
                })
                .expectComplete()
                .verify();
    }
}