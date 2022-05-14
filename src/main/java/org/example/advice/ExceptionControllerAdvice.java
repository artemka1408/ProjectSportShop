package org.example.advice;

import org.example.dto.ExceptionDTO;
import org.example.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    public ExceptionDTO handle(InvalidLimitException e) {
        e.printStackTrace();
        return new ExceptionDTO("incorrect_limit");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    public ExceptionDTO handle(InvalidOffsetException e) {
        e.printStackTrace();
        return new ExceptionDTO("incorrect_offset");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    public ExceptionDTO handle(NotAuthenticatedException e) {
        e.printStackTrace();
        return new ExceptionDTO("not_authorized");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)  // 403
    public ExceptionDTO handle(ForbiddenException e) {
        e.printStackTrace();
        return new ExceptionDTO("access_denied");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)  // 401
    public ExceptionDTO handle(PasswordNotMatchesException e) {
        e.printStackTrace();
        return new ExceptionDTO("incorrect_password");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)  // 403
    public ExceptionDTO handle(NotMakeReviewException e) {
        e.printStackTrace();
        return new ExceptionDTO("can't_comment");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    public ExceptionDTO handle(Exception e) {
        e.printStackTrace();
        return new ExceptionDTO("global_internal_error");
    }
}



