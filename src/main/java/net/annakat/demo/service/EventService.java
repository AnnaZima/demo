package net.annakat.demo.service;


import lombok.RequiredArgsConstructor;
import net.annakat.demo.model.Event;
import net.annakat.demo.model.Status;
import net.annakat.demo.repository.EventRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EventService {

    private  final EventRepository eventRepository;

    public Mono<Event> createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Mono<Event> getEventById(Integer id) {
        return eventRepository.findById(id);
    }

    public Flux<Event> getAllEventByUser(Integer userId) {
        return eventRepository.findAllEventsOfUser(userId);
    }

    public Mono<Boolean> deleteEvent(Integer id){
        return eventRepository.deleteEvent(id, String.valueOf(Status.DELETE));
    }
}
