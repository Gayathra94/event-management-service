package com.event.service.service.impl;

import com.event.service.dto.CreateEventRequest;
import com.event.service.exception.ApplicationException;
import com.event.service.mapper.EventMapper;
import com.event.service.model.Event;
import com.event.service.repository.EventRepository;
import com.event.service.service.EventService;
import com.event.service.util.EventUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event createEvent(CreateEventRequest createEventRequest) {
        try {
            Event event = EventMapper.toEvent(createEventRequest);
            return eventRepository.save(event);
        } catch (Exception e) {
            throw new ApplicationException("100001","error.create.event");
        }
    }
}
