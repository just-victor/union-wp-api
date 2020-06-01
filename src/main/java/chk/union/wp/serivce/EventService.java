package chk.union.wp.serivce;

import chk.union.wp.common.exception.NotFoundException;
import chk.union.wp.dto.EventDto;
import chk.union.wp.dto.UserDto;
import chk.union.wp.entity.Event;
import chk.union.wp.entity.User;
import chk.union.wp.mapper.EventMapper;
import chk.union.wp.repostiory.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserService userService;
    private final AuthService authService;

    public List<EventDto> getAll() {
        LOG.info("Retrieving all events");
        List<Event> events = eventRepository.findAll();

        return eventMapper.toDtos(events);
    }

    public EventDto getById(final Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());

        return eventMapper.toDto(event);
    }

    public EventDto save(final EventDto eventDto) {
        Event event = eventMapper.toEntity(eventDto, new Event());

        UserDto currentUserDto = authService.getCurrentUserDto();
        User user = userService.getById(currentUserDto.getId());
        event.setUser(user);

        LOG.info("Save new event for user: {}, userId: {}", user.getName(), user.getId());

        eventRepository.save(event);

        return eventMapper.toDto(event);
    }

    public EventDto update(final Long id, final EventDto eventDto) {
        LOG.info("Update event for user: {}, id: {}", eventDto.getUserId(), id);
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id: " + id + " not found"));

        eventMapper.toEntity(eventDto, event);
        return eventMapper.toDto(event);
    }

    public void delete(final Long id) {
        LOG.info("User {}, delete event by id: {}",  authService.getCurrentUserDto().getName(), id);
        eventRepository.deleteById(id);
    }
}
