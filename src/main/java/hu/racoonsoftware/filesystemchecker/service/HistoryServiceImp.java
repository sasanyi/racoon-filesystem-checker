package hu.racoonsoftware.filesystemchecker.service;

import hu.racoonsoftware.filesystemchecker.dto.HistoryDto;
import hu.racoonsoftware.filesystemchecker.mapper.HistoryMapper;
import hu.racoonsoftware.filesystemchecker.model.History;
import hu.racoonsoftware.filesystemchecker.repository.HistoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This class implementation of {@link hu.racoonsoftware.filesystemchecker.service.HistoryService}.
 * This implementation uses Database for store history entry
 */
@Service
public class HistoryServiceImp implements HistoryService{
    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    public HistoryServiceImp(HistoryRepository historyRepository, HistoryMapper historyMapper) {
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
    }

    /**
     * @return All history entries from store
     */
    @Override
    public Flux<HistoryDto> findAllHistory() {
        return this.historyRepository.findAll().map(historyMapper::historyToHistoryDto);
    }

    /**
     * @param name Filter expression for requestby
     * @return All history entries where requestby equal to name parameter
     */
    @Override
    public Flux<HistoryDto> findAllHistoryByName(String name) {
        return this.historyRepository.findAllByRequestBy(name).map(historyMapper::historyToHistoryDto);
    }

    /**
     * @param history The history entry which will save
     * @return The saved history entry
     */
    @Override
    public Mono<HistoryDto> saveHistory(History history) {
        history.setNew(true);
        return this.historyRepository.save(history).map(historyMapper::historyToHistoryDto);
    }

}
