package com.event.service.service.impl;

import com.event.service.dto.CreateEventRequest;
import com.event.service.dto.EventResponse;
import com.event.service.enums.EventVisibilityType;
import com.event.service.enums.UserRoleType;
import com.event.service.mapper.EventMapper;
import com.event.service.model.Event;
import com.event.service.model.User;
import com.event.service.repository.AttendanceRepository;
import com.event.service.repository.EventRepository;
import com.event.service.repository.UserRepository;
import com.event.service.service.EventService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventServiceImplIntegrationTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    private User testUser;
    @BeforeEach
    void setUpUser(){
        testUser = new User();
        testUser.setId("0fffb2b2-1638-4fa0-9f90-0b1f3e75945e");
        testUser.setUsername("ranjan");
        testUser.setPassword("ranjan123");
        testUser.setName("Ranjan");
        testUser.setEmail("ranjan@gmail.com");
        testUser.setRole(UserRoleType.USER);
        testUser.setCreatedAt(LocalDateTime.now());
        testUser = userRepository.save(testUser);
    }

    @Test
    @Order(1)
    void createEventSuccessfully(){
        CreateEventRequest request = new CreateEventRequest();
        request.setHostId(testUser.getId());
        request.setTitle("Test Event");
        request.setDescription("Event Description");
        request.setStartTime(LocalDateTime.now().plusDays(1));
        request.setEndTime(LocalDateTime.now().plusDays(2));
        request.setLocation("Negombo");
        request.setVisibility(EventVisibilityType.PRIVATE);
        Event event = EventMapper.toEvent(request, testUser);
        Event createdEvent = eventRepository.save(event);
        System.out.println(createdEvent);
        assertNotNull(createdEvent);
        assertEquals("Test Event", createdEvent.getTitle());
        assertEquals(testUser.getId(), createdEvent.getUser().getId());

    }

    @Test
    @Order(2)
    void shouldReturnUpcomingEvents() {
        EventResponse response = eventService.getListUpcomingEvents(0, 5);
        assertNotNull(response);
        assertTrue(response.getEventDTOList().size() >= 0);
    }

    @Test
    @Order(3)
    void shouldUpdateEventSuccessfully() {
        Event event = createDummyEvent();
        CreateEventRequest updateRequest = new CreateEventRequest();
        updateRequest.setId(event.getId());
        updateRequest.setTitle("Updated Event");
        updateRequest.setDescription("Updated Description");
        updateRequest.setStartTime(LocalDateTime.now().plusDays(3));
        updateRequest.setEndTime(LocalDateTime.now().plusDays(4));
        updateRequest.setLocation("Colombo");
        updateRequest.setVisibility(EventVisibilityType.PRIVATE);
        Event updated = eventService.updateEvent(updateRequest);
        assertEquals("Updated Event", updated.getTitle());
    }

    @Test
    @Order(4)
    void shouldDeleteEventSuccessfully() {
        Event event = createDummyEvent();
        eventService.deleteEvent(event.getId());
        Optional<Event> deleted = eventRepository.findById(event.getId());
        assertTrue(deleted.isEmpty());
    }

    private Event createDummyEvent() {
        Event event = new Event();
        event.setId(UUID.randomUUID().toString());
        event.setTitle("Test Event");
        event.setDescription("Event Desc");
        event.setStartTime(LocalDateTime.now().plusDays(1));
        event.setEndTime(LocalDateTime.now().plusDays(2));
        event.setLocation("Kandy");
        event.setVisibility(EventVisibilityType.PUBLIC);
        event.setCreatedAt(LocalDateTime.now());
        event.setUser(testUser);
        return eventRepository.save(event);
    }
}
