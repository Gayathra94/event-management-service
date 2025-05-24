package com.event.service.model;

import com.event.service.enums.EventVisibilityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ems_event")
public class Event {

  @Id
  private String id;
  private String title;
  private String description;
  private String hostId;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String location;
  @Enumerated(EnumType.STRING)
  private EventVisibilityType visibility;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
