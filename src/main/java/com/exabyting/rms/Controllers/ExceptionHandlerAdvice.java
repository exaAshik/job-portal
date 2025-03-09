package com.exabyting.rms.Controllers;

import com.exabyting.rms.Exception.ApiException;
import com.exabyting.rms.Exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(CustomException.class)
    ResponseEntity<?>handleApiException(CustomException ex){
        return new ResponseEntity<>(new ApiException(ex.getMessage(), ex.getStatus()),ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiException> handleApiException(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        ApiException apiException = new ApiException(details.toString(), HttpStatus.EXPECTATION_FAILED);
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
