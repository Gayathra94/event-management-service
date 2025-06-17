package com.event.service.model;

import com.event.service.enums.AttendanceType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ems_attendance")
@ToString // no exclude parameter here
public class Attendance implements Serializable {

  @EmbeddedId
  private AttendanceId id = new AttendanceId();

  @ManyToOne
  @MapsId("eventId")
  @JoinColumn(name = "event_id")
  @ToString.Exclude
  private Event event;

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  @ToString.Exclude
  private User user;

  @Enumerated(EnumType.STRING)
  private AttendanceType status;

  private LocalDateTime attendedAt = LocalDateTime.now();
}
