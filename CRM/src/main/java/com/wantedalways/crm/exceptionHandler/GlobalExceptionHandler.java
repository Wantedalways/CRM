package com.wantedalways.crm.exceptionHandler;

import com.wantedalways.crm.exception.ActivityException;
import com.wantedalways.crm.exception.LoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = LoginException.class)
    @ResponseBody
    public Map<String,Object> doLoginException(Exception e) {

        Map<String,Object> exceptionMap = new HashMap<>();
        exceptionMap.put("success",false);
        exceptionMap.put("msg",e.getMessage());

        return exceptionMap;
    }

    @ExceptionHandler(value = ActivityException.class)
    @ResponseBody
    public Map<String,Object> doDeleteException(Exception e) {

        Map<String,Object> exceptionMap = new HashMap<>();
        exceptionMap.put("success",false);
        exceptionMap.put("msg",e.getMessage());

        return exceptionMap;
    }
}
