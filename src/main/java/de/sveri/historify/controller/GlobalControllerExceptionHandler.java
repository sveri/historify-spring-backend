package de.sveri.historify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.sveri.historify.controller.exception.InvalidLoginException;
import de.sveri.historify.controller.exception.UnprocessableEntityException;
import de.sveri.historify.controller.rest.ErrorResponse;
import de.sveri.historify.controller.rest.HistoryApi;
import de.sveri.historify.controller.rest.Response;
import lombok.extern.apachecommons.CommonsLog;

@ControllerAdvice
@CommonsLog
class GlobalControllerExceptionHandler {

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(InvalidLoginException.class)
	public ResponseEntity<Response> handleConflict() {
		Response r = new ErrorResponse(HistoryApi.UNPROCESSABLE_TAG_ID, "Invalid Login.");
		log.error("Invalid Login");
		return new ResponseEntity<>(r, HttpStatus.UNAUTHORIZED);
	}

	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(UnprocessableEntityException.class)
	public ResponseEntity<Response> handleUnprocessableEntity() {
		Response r = new ErrorResponse(HistoryApi.UNPROCESSABLE_TAG_ID, "Cannot Process Tags.");
		log.error("Entity contains invalid data.");
		return new ResponseEntity<>(r, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}