package com.event.service.repository;

import com.event.service.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends CrudRepository<Event,String > {

    List<Event> findByStartTimeAfter(LocalDateTime now);


    @Query("""
    select distinct e from Event e
    left join fetch e.attendances
    where e.startTime > :now
    """)
    List<Event> findUpcomingEvents(@Param("now") LocalDateTime now);


    @Query("""
    select distinct e from Event e
    left join e.attendances a
    where e.user.id = :userId OR a.user.id = :userId
    """)
    List<Event> findAllEventsUserIsHostingOrAttending(@Param("userId") String userId);

}
