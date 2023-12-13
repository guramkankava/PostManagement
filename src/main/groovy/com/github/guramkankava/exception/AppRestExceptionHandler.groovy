package com.github.guramkankava.exception

import com.github.guramkankava.response.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.http.HttpStatus

@RestControllerAdvice
class AppRestExceptionHandler {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    ResponseEntity<Object> handlePostNotFound(PostNotFoundException postNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message(postNotFoundException.getMessage()).build())
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    ResponseEntity<Object> handleForbiddenException(ForbiddenException forbiddenException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.builder().message(forbiddenException.getMessage()).build())
    }

}
