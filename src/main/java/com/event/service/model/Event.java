package com.event.service.model;

import com.event.service.enums.EventVisibilityType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Event {

  @Id
  private String id;
  private String title;
  private String description;
  private String hostId;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String location;
  private EventVisibilityType visibility;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
