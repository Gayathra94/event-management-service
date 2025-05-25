package com.event.service.dto;

import com.event.service.enums.AttendanceType;
import com.event.service.model.Attendance;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AttendanceDTO {
    private String eventId;
    private String userId;
    private String userName;
    private AttendanceType status;
    private LocalDateTime attendedAt;

    public AttendanceDTO(Attendance attendance) {
        this.eventId = attendance.getEvent().getId();
        this.userId = attendance.getUser().getId();
        this.userName = attendance.getUser().getName();
        this.status = attendance.getStatus();
        this.attendedAt = attendance.getAttendedAt();
    }

}