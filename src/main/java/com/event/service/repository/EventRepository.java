package com.event.service.repository;

import com.event.service.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event,String > {
}
