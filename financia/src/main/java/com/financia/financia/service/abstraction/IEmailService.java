package com.financia.financia.service.abstraction;

import jakarta.mail.MessagingException;

public interface IEmailService {

    void sendActivationLinkEmail(String email, String token, String username) throws MessagingException;
}
