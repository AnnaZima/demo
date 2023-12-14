package net.annakat.demo.rest;

import lombok.RequiredArgsConstructor;
import net.annakat.demo.dto.EventDto;
import net.annakat.demo.mapper.EventMapper;
import net.annakat.demo.model.Event;
import net.annakat.demo.model.User;
import net.annakat.demo.service.EventService;
import net.annakat.demo.service.FileService;
import net.annakat.demo.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")

public class EventRestControllerV1 {

    private final EventService eventService;
    private final UserService userService;
    private final EventMapper eventMapper;



    @GetMapping("/{id}")
    public Mono<Event> getEvent(@PathVariable("id") Integer id) {
        return eventService.getEventById(id);
    }

    @GetMapping()
    public Flux<Event> getAllEventByUsername(@RequestBody String username) {
       Mono<User> user = userService.getByUserName(username);
        return eventService.getAllEventByUser(Objects.requireNonNull(user.block()).getId());
    }

    @PostMapping("/{id}")
    public Mono<Boolean> deleteEvent(@PathVariable("id") Integer id) {
        return eventService.deleteEvent(id);
    }

}
