package org.example.roomsense.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail detailAboutProblem(DataIntegrityViolationException dataIntegrityViolationException){
    String message = dataIntegrityViolationException.getMostSpecificCause().getMessage();
    if(message.contains("no_overlap")) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Reservation time is overlapping.");
    } else {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid SQL request.");
    }

    }

}
