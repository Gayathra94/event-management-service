package com.event.service.repository;

import com.event.service.model.Attendance;
import com.event.service.model.AttendanceId;
import com.event.service.model.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttendanceRepository extends CrudRepository<Attendance, AttendanceId> {

    List<Attendance> findByEvent(Event event);
}
