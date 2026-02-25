package com.mikey.ticketing_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String full_name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password_hash;

    @Column(nullable = false)
    private String role;

    private boolean isActive = true;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime created_at;

}
