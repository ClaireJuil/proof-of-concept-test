/**
 * (c) Copyright 1998-2021, ANS. All rights reserved.
 */
package fr.ans.psc.dam.api.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//https://www.baeldung.com/exception-handling-for-rest-with-spring

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	  @ExceptionHandler(value 
		      = { DamRequestException.class}) 
		    protected ResponseEntity<Object> handleConflict(
		      RuntimeException ex, WebRequest request) {	      
		  DamRequestException except = (DamRequestException)ex;
		        return handleExceptionInternal(ex, except.getErreur(), 
		          new HttpHeaders(), except.getStatusARetourner(), request);
		    }
}
 