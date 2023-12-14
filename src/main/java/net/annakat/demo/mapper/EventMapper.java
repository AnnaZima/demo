package net.annakat.demo.mapper;

import net.annakat.demo.dto.EventDto;
import net.annakat.demo.model.Event;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventDto map(Event event);

    @InheritInverseConfiguration
    Event map(EventDto dto);
}
