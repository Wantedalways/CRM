package com.wantedalways.crm.exception;

import org.springframework.stereotype.Component;

@Component
public class ActivityException extends Exception{

    public ActivityException() {
        super();
    }

    public ActivityException(String message) {
        super(message);
    }
}
