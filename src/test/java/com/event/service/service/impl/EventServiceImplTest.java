package com.event.service.service.impl;

import com.event.service.dto.CreateEventRequest;
import com.event.service.model.Event;
import com.event.service.model.User;
import com.event.service.repository.AttendanceRepository;
import com.event.service.repository.EventRepository;
import com.event.service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceImplUnitTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Test
    void testCreateEventSuccessWithMockData() {
        User user = new User();
        user.setId("dasd45-454hgj-gfd54-fgd5");
        CreateEventRequest request = new CreateEventRequest();
        request.setHostId("dasd45-454hgj-gfd54-fgd5");
        request.setTitle("Unit Test Event");
        Event event = new Event();
        event.setTitle("Unit Test Event");
        when(userRepository.findUserById("dasd45-454hgj-gfd54-fgd5")).thenReturn(user);
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        Event result = eventService.createEvent(request);
        assertEquals("Unit Test Event", result.getTitle());
    }
}
