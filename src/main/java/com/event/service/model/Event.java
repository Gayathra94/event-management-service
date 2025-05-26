package com.event.service.model;

import com.event.service.enums.EventVisibilityType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ems_event")
@ToString(exclude = {"user", "attendances"})
public class Event {

  @Id
  private String id;
  private String title;
  private String description;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String location;
  @Enumerated(EnumType.STRING)
  private EventVisibilityType visibility;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "host_id", nullable = false)
  private User user;

  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Attendance> attendances;


}