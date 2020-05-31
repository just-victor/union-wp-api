package chk.union.wp.controller;

import chk.union.wp.common.response.ResponseWrapper;
import chk.union.wp.dto.EventDto;
import chk.union.wp.serivce.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("ping")
    public String ping() {
        return "i'm alive";
    }

    @GetMapping
    public ResponseWrapper<List<EventDto>> getAll() {
        return new ResponseWrapper<>(eventService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseWrapper<EventDto> getById(@PathVariable Long id) {
        return ResponseWrapper.of(eventService.getById(id));
    }

    @PostMapping
    public ResponseWrapper<EventDto> save(@RequestBody EventDto eventDto) {
        return ResponseWrapper.of(eventService.save(eventDto));
    }

    @PutMapping("/{id}")
    public ResponseWrapper<EventDto> update(@PathVariable Long id, @RequestBody EventDto eventDto) {
        return ResponseWrapper.of(eventService.update(id, eventDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        eventService.delete(id);

        return ResponseEntity.ok().build();
    }

}
