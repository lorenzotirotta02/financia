package com.financia.financia.controller;


import com.financia.financia.dto.ErrorResult;
import com.financia.financia.dto.LoginDTO;
import com.financia.financia.dto.RegistrationDTO;
import com.financia.financia.dto.UserDTO;
import com.financia.financia.exception.TokenNonPresenteException;
import com.financia.financia.exception.TokenNonValidoException;
import com.financia.financia.exception.UserGiaEsistenteException;
import com.financia.financia.model.User;
import com.financia.financia.security.JWTUtil;
import com.financia.financia.service.TokenService;
import com.financia.financia.service.implementation.EmailService;
import com.financia.financia.service.implementation.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.JobKOctets;
import java.util.Collections;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final TokenService tokenService;

    private final UserService userService;

    private final EmailService emailService;

    private final JWTUtil jwtUtil;

    private final AuthenticationManager authManager;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegistrationDTO registrationDTO){
        try{
            if (!userService.existsByEmail(registrationDTO.getEmail()) &&
                    !userService.existsByUsername(registrationDTO.getUsername())) {

                User user = userService.saveUser(registrationDTO);
                String token = tokenService.createValidationTokenForUser(user);

                emailService.sendActivationLinkEmail(registrationDTO.getEmail(), token, registrationDTO.getUsername());
                return ResponseEntity.status(HttpStatus.CREATED).body(token);
            } else {
                String message = "Utente già esistente con questa email o username";
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ErrorResult(
                                Integer.toString(HttpStatus.CONFLICT.value()),
                                message,
                                null));
            }
        }catch (UserGiaEsistenteException e){
            log.error("Errore nella registrazione: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResult(
                            Integer.toString(HttpStatus.CONFLICT.value()),
                            e.getMessage(),
                            null));
        }catch (MessagingException e){
            log.error("Errore nell'invio dell'email", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResult(
                            Integer.toString(HttpStatus.BAD_REQUEST.value()),
                            e.getMessage(), null));

        } catch (Exception exception) {
            log.error("INTERNAL SERVER ERROR", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResult(
                            Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                            exception.getMessage(),
                            null
                    ));
        }
    }
    @PostMapping("/activate/{token}")
    public ResponseEntity<Object> activateAccount(@PathVariable String token) {
        try {
            userService.activateUserAccount(token);
            return ResponseEntity.ok("Account attivato con successo.");
        } catch (TokenNonPresenteException | TokenNonValidoException e) {
            log.error("Errore nel token", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResult(
                            Integer.toString(HttpStatus.BAD_REQUEST.value()),
                            e.getMessage(), null));

        } catch (Exception exception) {
            log.error("INTERNAL SERVER ERROR", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResult(
                            Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                            exception.getMessage(),
                            null
                    ));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid LoginDTO loginDTO) {
        try{

            User user = userService.findUser(loginDTO);

            UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(loginDTO.getUsername());


            // Respond with the JWT
            return ResponseEntity.ok(token);



        }catch (UserGiaEsistenteException e){
            log.error("Errore nel login: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResult(Integer.toString(HttpStatus.BAD_REQUEST.value()),
                            e.getMessage(), null));
        }catch (Exception e) {
            log.error("INTERNAL SERVER ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResult(Integer.toString(HttpStatus.BAD_REQUEST.value()),
                            e.getMessage(), null));
        }
    }

}
