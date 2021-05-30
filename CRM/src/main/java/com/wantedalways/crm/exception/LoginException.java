package com.wantedalways.crm.exception;

import org.springframework.stereotype.Component;

@Component
public class LoginException extends Exception{

    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }
}
