package hu.racoonsoftware.filesystemchecker.service;

import hu.racoonsoftware.filesystemchecker.dto.HistoryDto;
import hu.racoonsoftware.filesystemchecker.model.History;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This interface define methods of HistoryService. This implementation of this interface responsible for store, query
 * of history entries.
 */
public interface HistoryService {
    /**
     * @return All history entries from store
     */
    Flux<HistoryDto> findAllHistory();

    /**
     * @param name Filter expression for requestby
     * @return All history entries where requestby equal to name parameter
     */
    Flux<HistoryDto> findAllHistoryByName(String name);

    /**
     * @param history The history entry which will save
     * @return The saved history entry
     */
    Mono<HistoryDto> saveHistory(History history);

}
