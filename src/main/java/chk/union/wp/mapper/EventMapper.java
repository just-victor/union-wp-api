package chk.union.wp.mapper;

import chk.union.wp.dto.EventDto;
import chk.union.wp.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper
public interface EventMapper {
    EventDto toDto(Event entity);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Event toEntity(EventDto dto, @MappingTarget Event event);

    List<EventDto> toDtos(Collection<Event> entities);
//    List<Event> toEntities(Collection<EventDto> dto);
}
