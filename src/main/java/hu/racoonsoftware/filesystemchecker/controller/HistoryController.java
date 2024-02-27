package hu.racoonsoftware.filesystemchecker.controller;

import hu.racoonsoftware.filesystemchecker.dto.HistoryDto;
import hu.racoonsoftware.filesystemchecker.model.History;
import hu.racoonsoftware.filesystemchecker.repository.HistoryRepository;
import hu.racoonsoftware.filesystemchecker.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
/**
 * This class define all RESAT API routs for /api/history. These REST endpoints documented in swagger ui
 */
@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    /**
     * @param nameFilter Optional parameter for get specific user search history
     * @return All saved searching history or searching history for specific user
     */
    @Operation(
            summary = "Get history",
            description = "Searching history. Can filter for user"
    )
    @GetMapping()
    public Flux<HistoryDto> getHistory(@RequestParam(value = "name", required = false) String nameFilter) {
        if(nameFilter != null && !nameFilter.isEmpty()){
            return this.historyService.findAllHistoryByName(nameFilter);
        }

        return this.historyService.findAllHistory();
    }
}
