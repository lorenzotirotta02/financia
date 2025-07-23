package com.financia.financia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "validate_account_token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidateAccountToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime expirationTime;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

}
