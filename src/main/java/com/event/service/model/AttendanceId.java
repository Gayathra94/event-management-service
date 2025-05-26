package com.event.service.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@ToString
public class AttendanceId implements Serializable {
    private String eventId;
    private String userId;
}
