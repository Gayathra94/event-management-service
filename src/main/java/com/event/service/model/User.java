package com.event.service.model;

import com.event.service.enums.UserRoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ems_user")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRoleType role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
