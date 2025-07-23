package com.financia.financia.repository;

import com.financia.financia.model.ValidateAccountToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ValidateTokenRepository extends JpaRepository<ValidateAccountToken, Long> {

    Optional<ValidateAccountToken> findByToken(String token);

    List<ValidateAccountToken> findAllByExpirationTimeBefore(LocalDateTime date);
}
