package com.event.service.repository;

import com.event.service.model.Event;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends CrudRepository<Event,String > {
    List<Event> findByStartTimeAfter(LocalDateTime now);

}
