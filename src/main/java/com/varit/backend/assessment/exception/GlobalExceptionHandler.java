package com.varit.backend.assessment.exception;

import com.varit.backend.assessment.model.response.ResponseModel;
import com.varit.backend.assessment.model.response.ResponseStatus;
import com.varit.backend.assessment.model.response.ResponseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseModel<Void>> handleBusinessException(BusinessException ex) {
        log.error("Got business exception", ex);
        var responseStatus = new ResponseStatus(ResponseStatusEnum.BUSINESS_ERROR, ex.getMessage());
        var response = new ResponseModel<Void>();
        response.setStatus(responseStatus);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ResponseModel<Void>> handleDataNotFoundException(DataNotFoundException ex) {
        log.error("Got data not found exception", ex);
        var responseStatus = new ResponseStatus(ResponseStatusEnum.NOT_FOUND, ex.getMessage());
        var response = new ResponseModel<Void>();
        response.setStatus(responseStatus);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseModel<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Got invalid input", ex);
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        var responseStatus = new ResponseStatus(ResponseStatusEnum.INPUT_VALIDATION_ERROR, StringUtils.join(errors));
        var response = new ResponseModel<Void>();
        response.setStatus(responseStatus);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseModel<Void>> handleException(Exception ex) {
        log.error("Got exception", ex);
        var responseStatus = new ResponseStatus(ResponseStatusEnum.INTERNAL_ERROR, ex.getMessage());
        var response = new ResponseModel<Void>();
        response.setStatus(responseStatus);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

}
