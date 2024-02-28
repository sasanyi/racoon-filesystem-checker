package hu.racoonsoftware.filesystemchecker.mapper;

import hu.racoonsoftware.filesystemchecker.dto.HistoryDto;
import hu.racoonsoftware.filesystemchecker.model.History;
import org.mapstruct.Mapper;

/**
 * This interface define the mappers of History entity
 */
@Mapper(componentModel = "spring")
public interface HistoryMapper {
    /**
     * @param history A history entity object
     * @return API representation of history entity object
     */
    HistoryDto historyToHistoryDto(History history);
}
