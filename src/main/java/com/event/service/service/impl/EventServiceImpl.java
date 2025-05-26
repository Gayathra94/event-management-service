package com.event.service.service.impl;

import com.event.service.dto.AttendanceDTO;
import com.event.service.dto.CreateEventRequest;
import com.event.service.dto.EventDTO;
import com.event.service.dto.EventResponse;
import com.event.service.exception.ApplicationException;
import com.event.service.mapper.EventMapper;
import com.event.service.model.Attendance;
import com.event.service.model.AttendanceId;
import com.event.service.model.Event;
import com.event.service.model.User;
import com.event.service.repository.AttendanceRepository;
import com.event.service.repository.EventRepository;
import com.event.service.repository.UserRepository;
import com.event.service.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public Event createEvent(CreateEventRequest createEventRequest) {
        try {
            User user = userRepository.findUserById(createEventRequest.getHostId());
            Event event = EventMapper.toEvent(createEventRequest, user);
            return eventRepository.save(event);
        } catch (Exception e) {
            throw new ApplicationException("1001","error.create.event");
        }
    }

    @Override
    public EventResponse getListUpcomingEvents(int page, int size) {
        int offset = page * size;
        LocalDateTime now = LocalDateTime.now();
        List<Event> events = eventRepository.findUpcomingEventsWithPagination(now, size, offset);
        List<EventDTO> eventDTOList  = events.stream().map(event -> new EventDTO(event)).collect(Collectors.toList());
        long total = eventRepository.countUpcomingEvents(now);
        return new EventResponse(eventDTOList, total);

    }

    @Override
    public Event updateEvent(CreateEventRequest createEventRequest) {
        try {
            if (createEventRequest.getId() != null) {

                Event existingEvent = eventRepository.findById(createEventRequest.getId()).orElseThrow();
                existingEvent.setTitle(createEventRequest.getTitle());
                existingEvent.setDescription(createEventRequest.getDescription());
                existingEvent.setStartTime(createEventRequest.getStartTime());
                existingEvent.setEndTime(createEventRequest.getEndTime());
                existingEvent.setLocation(createEventRequest.getLocation());
                existingEvent.setVisibility(createEventRequest.getVisibility());
                existingEvent.setUpdatedAt(LocalDateTime.now());
                return eventRepository.save(existingEvent);
            } else {
                throw new ApplicationException("1003", "invalid.request.missing.id");
            }
        } catch (Exception e) {
            throw new ApplicationException("1003", "error.update.event");
        }
    }

    @Override
    @Transactional
    public void deleteEvent(String eventId) {
        try {
            Event event = eventRepository.findById(eventId).orElseThrow(() -> new ApplicationException("1004", "event.not.found"));
            int size = event.getAttendances().size();
            eventRepository.delete(event); // safer than deleteById
        } catch (Exception e) {
            throw new ApplicationException("1005", "error.delete.event");
        }

    }

    @Override
    public void attendEvent(AttendanceDTO attendanceDTO) {
        try {
            Event event = eventRepository.findById(attendanceDTO.getEventId()).orElseThrow();
            User user = userRepository.findById(attendanceDTO.getUserId()).orElseThrow();
            AttendanceId attendanceId = new AttendanceId(event.getId(), user.getId());

            Optional<Attendance> existingAttendance = attendanceRepository.findById(attendanceId);
            if(existingAttendance.isPresent()){
                existingAttendance.get().setStatus(attendanceDTO.getStatus());
                attendanceRepository.save(existingAttendance.get());
            }else{
                Attendance attendance = new Attendance();
                attendance.setId(attendanceId);
                attendance.setEvent(event);
                attendance.setUser(user);
                attendance.setStatus(attendanceDTO.getStatus());
                attendanceRepository.save(attendance);
            }
        }catch (Exception e){
            throw new ApplicationException("1005", "error.update.event");
        }
    }

    @Override
    public List<EventDTO> getListEventsUserHostingOrAttend(String userId) {
        List<Event> events =  eventRepository.findAllEventsUserIsHostingOrAttending(userId);
        return events.stream().map(event -> new EventDTO(event)).collect(Collectors.toList());
    }
}
