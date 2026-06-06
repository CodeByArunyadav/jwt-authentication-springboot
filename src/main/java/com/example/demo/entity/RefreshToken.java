package com.example.demo.entity; 

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 500)
    private String tokenHash;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, length = 500)
    private String userAgentHash;

    @Column(nullable = false, length = 500)
    private String languageHash;

    @Column(nullable = false, length = 500)
    private String timezoneHash;

    @Column(nullable = false)
    private boolean revoked;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    
}
