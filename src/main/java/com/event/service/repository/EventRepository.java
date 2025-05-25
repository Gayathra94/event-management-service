package com.event.service.repository;

import com.event.service.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends CrudRepository<Event,String > {
    List<Event> findByStartTimeAfter(LocalDateTime now);
    List<Event> findByUserId(String userId);
    List<Event> findByAttendances_User_Id(String userId);

    @Query("""
    SELECT DISTINCT e
    FROM Event e
    LEFT JOIN e.attendances a
    WHERE e.user.id = :userId OR a.user.id = :userId
""")
    List<Event> findAllEventsUserIsHostingOrAttending(@Param("userId") String userId);

}
