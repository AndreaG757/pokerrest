package com.projectpokerrest.pokerrest.web.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class UtenteNotAuthorizedException extends RuntimeException {

    public UtenteNotAuthorizedException(String message) {super(message);}

}
