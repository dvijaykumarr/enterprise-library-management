package com.vijay.exception;


import com.vijay.payload.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(GenreException.class)
    public ResponseEntity<ApiResponse> handleGenreException(GenreException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(e.getMessage(),false));
    }


    @ExceptionHandler(BookException.class)
    public ResponseEntity<ApiResponse> handleBookException(BookException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(e.getMessage(), false));
    }

}
