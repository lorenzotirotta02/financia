package com.financia.financia.service.abstraction;


import com.financia.financia.dto.LoginDTO;
import com.financia.financia.dto.RegistrationDTO;
import com.financia.financia.model.User;

public interface IUserService {

    User saveUser(RegistrationDTO registrationDTO);

    User findUser(LoginDTO loginDTO);

    void activateUserAccount(String token);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
