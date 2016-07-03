package de.sveri.historify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.sveri.historify.controller.exception.InvalidLoginException;
import lombok.extern.apachecommons.CommonsLog;

@ControllerAdvice
@CommonsLog
class GlobalControllerExceptionHandler {
 
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidLoginException.class)
    public void handleConflict() {
        log.error("Invalid Login");
    }
}