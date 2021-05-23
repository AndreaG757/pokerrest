package com.projectpokerrest.pokerrest.web.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TavoloNotFoundException extends RuntimeException {

    public TavoloNotFoundException(String message) {super(message);}

}
