package com.github.guramkankava.exception

import com.github.guramkankava.response.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.http.HttpStatus

@RestControllerAdvice
class AppRestExceptionHandler {


    @ExceptionHandler(PostNotFoundException.class)
    ResponseEntity<Object> handlePostNotFound(PostNotFoundException postNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message(postNotFoundException.getMessage()).build())
    }
}
