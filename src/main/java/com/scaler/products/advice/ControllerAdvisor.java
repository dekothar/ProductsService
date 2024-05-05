package com.scaler.products.advice;


import com.scaler.products.dto.ErrorsDto;
import com.scaler.products.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorsDto> handleExceptions(final ProductNotFoundException exception) {
        ErrorsDto errorDto = new ErrorsDto();
        errorDto.setMessage(exception.getMessage());
        ResponseEntity<ErrorsDto> errorMsg = new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        return errorMsg;
    }
}