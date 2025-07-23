package com.financia.financia.service;

import com.financia.financia.exception.TokenNonPresenteException;
import com.financia.financia.exception.TokenNonValidoException;
import com.financia.financia.model.User;
import com.financia.financia.model.ValidateAccountToken;
import com.financia.financia.repository.UserRepository;
import com.financia.financia.repository.ValidateTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final ValidateTokenRepository validateTokenRepository;

    private final UserRepository userRepository;


    public String createValidationTokenForUser(User user) {
        // Generate a unique token
        String token = UUID.randomUUID().toString();

        // Set expiration (24 hours from now, but as a LocalDate)
        LocalDateTime expireDate = LocalDateTime.now().plusHours(24);

        // Create and save token in database
        ValidateAccountToken passwordResetToken = new ValidateAccountToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpirationTime(LocalDateTime.now().plusHours(24));
        validateTokenRepository.save(passwordResetToken);

        return token;
    }

    public void validateAccountActivationToken(String token) {
        Optional<ValidateAccountToken> tokenOptional = validateTokenRepository.findByToken(token);
        if (tokenOptional.isEmpty()) {
            throw new TokenNonPresenteException("Token non presente.");
        }

        ValidateAccountToken activationToken = tokenOptional.get();
        if (activationToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new TokenNonValidoException("Token scaduto.");
        }
    }

    @Scheduled(fixedRate = 3600000) // ogni ora
    public void deleteTokens() {
        LocalDateTime now = LocalDateTime.now();

        List<ValidateAccountToken> tokensToDelete = validateTokenRepository
                .findAllByExpirationTimeBefore(now);

        if (!tokensToDelete.isEmpty()) {
            validateTokenRepository.deleteAll(tokensToDelete);
            System.out.println("Deleted " + tokensToDelete.size() + " expired tokens");
        }
    }
}
