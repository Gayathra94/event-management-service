package com.event.service.mapper;

import com.event.service.dto.CreateEventRequest;
import com.event.service.model.Event;
import com.event.service.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class EventMapper {

    public static Event toEvent(CreateEventRequest request, User user) {
        Event event = new Event();
        event.setId(UUID.randomUUID().toString()); // generate unique ID
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setUser(user);
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setLocation(request.getLocation());
        event.setVisibility(request.getVisibility());
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        return event;
    }
}
