package com.financia.financia.service.implementation;

import com.financia.financia.dto.LoginDTO;
import com.financia.financia.dto.RegistrationDTO;
import com.financia.financia.exception.TokenNonPresenteException;
import com.financia.financia.exception.TokenNonValidoException;
import com.financia.financia.exception.UserGiaEsistenteException;
import com.financia.financia.exception.UserNonTrovatoException;
import com.financia.financia.model.User;
import com.financia.financia.model.UserRole;
import com.financia.financia.model.ValidateAccountToken;
import com.financia.financia.repository.UserRepository;
import com.financia.financia.repository.ValidateTokenRepository;
import com.financia.financia.service.TokenService;
import com.financia.financia.service.abstraction.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ValidateTokenRepository validateTokenRepository;
    private final TokenService tokenService;

    @Override
    public User saveUser(RegistrationDTO registrationDTO) {
        if(registrationDTO != null) {
            User user = new User();
            user.setEmail(registrationDTO.getEmail());
            user.setUsername(registrationDTO.getUsername());
            user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
            user.setRole(UserRole.NON_ATTIVO);
            return userRepository.save(user);
        }
        throw new RuntimeException("Il dto è vuoto");
    }

    @Override
    public User findUser(LoginDTO loginDTO) {
        if(loginDTO != null) {
            Optional<User> user = userRepository.findByUsername(loginDTO.getUsername());
            if(user.isPresent()) {
                return user.get();
            }
            throw new UserNonTrovatoException("User non trovato");
        }
        throw new RuntimeException("Il dto è vuoto");
    }

    @Override
    public void activateUserAccount(String token) {
        tokenService.validateAccountActivationToken(token);
        Optional<ValidateAccountToken> activationToken = validateTokenRepository.findByToken(token);
        User user = activationToken.get().getUser();
        user.setRole(UserRole.ATTIVO);
        userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        boolean trovato = userRepository.findByEmail(email).isPresent();
        if (trovato) {
            throw new UserGiaEsistenteException("User con questa email già esistente");
        }
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        boolean trovato = userRepository.findByUsername(username).isPresent();
        if (trovato) {
            throw new UserGiaEsistenteException("User con questo username già esistente");
        }
        return false;
    }
}
