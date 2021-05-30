package com.wantedalways.crm.exception;

import org.springframework.stereotype.Component;

@Component
public class DMLException extends Exception{

    public DMLException() {
        super();
    }

    public DMLException(String message) {
        super(message);
    }
}
