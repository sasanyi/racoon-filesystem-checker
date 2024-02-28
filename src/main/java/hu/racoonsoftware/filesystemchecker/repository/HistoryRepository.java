package hu.racoonsoftware.filesystemchecker.repository;

import hu.racoonsoftware.filesystemchecker.model.History;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

/**
 * This interface define the operations on history table
 */
public interface HistoryRepository extends ReactiveCrudRepository<History, UUID> {
    /**
     * @param requestBy Filter expression for history table requestby column
     * @return All history entry where the requestby equivalent with requestBy parameter
     */
    Flux<History> findAllByRequestBy(String requestBy);
}
