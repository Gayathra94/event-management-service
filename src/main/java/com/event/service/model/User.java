package com.event.service.model;

import com.event.service.enums.UserRoleType;
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
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String name;
    private String email;
    private UserRoleType role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
