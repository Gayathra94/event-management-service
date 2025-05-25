package com.event.service.dto;

import com.event.service.enums.EventVisibilityType;
import com.event.service.model.Event;
import com.event.service.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EventDTO {

    private String id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private EventVisibilityType visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String hostId;
    private String hostName;

    public EventDTO(Event event) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.startTime = event.getStartTime();
        this.endTime = event.getEndTime();
        this.location = event.getLocation();
        this.visibility = event.getVisibility();
        this.createdAt = event.getCreatedAt();
        this.updatedAt = event.getUpdatedAt();

        User host = event.getUser();
        if (host != null) {
            this.hostId = host.getId();
            this.hostName = host.getName();
        }
    }
}
