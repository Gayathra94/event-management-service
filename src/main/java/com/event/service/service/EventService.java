package com.event.service.service;

import com.event.service.dto.AttendanceDTO;
import com.event.service.dto.CreateEventRequest;
import com.event.service.dto.EventDTO;
import com.event.service.dto.EventResponse;
import com.event.service.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    Event createEvent(CreateEventRequest createEventRequest);

    EventResponse getListUpcomingEvents(int page, int size);

    Event updateEvent(CreateEventRequest createEventRequest);

    void deleteEvent(String eventId);

    void attendEvent(AttendanceDTO attendanceDTO);

    List<EventDTO> getListEventsUserHostingOrAttend(String userId);
}
