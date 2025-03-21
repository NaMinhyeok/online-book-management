package org.querypie.bookmanagement.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.querypie.bookmanagement.common.support.error.CustomException;
import org.querypie.bookmanagement.common.support.error.ErrorType;
import org.querypie.bookmanagement.common.support.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e) {
        switch (e.getErrorType().getLogLevel()) {
            case ERROR -> log.error("CustomException : {}", e.getMessage(), e);
            case WARN -> log.warn("CustomException : {}", e.getMessage(), e);
            default -> log.info("CustomException : {}", e.getMessage(), e);
        }
        return new ResponseEntity<>(
            ApiResponse.error(e.getErrorType(), e.getData()), e.getErrorType().getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        log.debug("Validation error occurred: {}", e.getMessage(), e);

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<Map<String, String>> validationErrors =
            fieldErrors.stream()
                .map(
                    error ->
                        Map.of(
                            "field", error.getField(),
                            "message", error.getDefaultMessage()))
                .toList();

        return new ResponseEntity<>(
            ApiResponse.error(ErrorType.METHOD_ARGUMENT_NOT_VALID, validationErrors),
            ErrorType.METHOD_ARGUMENT_NOT_VALID.getStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<?>> handleBindException(BindException e) {
        log.warn("Binding error occurred: {}", e.getMessage(), e);

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<Map<String, String>> validationErrors =
            fieldErrors.stream()
                .map(
                    error ->
                        Map.of(
                            "field", error.getField(),
                            "message", error.getDefaultMessage()))
                .toList();

        return new ResponseEntity<>(
            ApiResponse.error(ErrorType.BINDING_ERROR, validationErrors),
            ErrorType.BINDING_ERROR.getStatus());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse<?>> handleHandlerMethodValidationException(
        HandlerMethodValidationException e) {
        log.warn("Validation error occurred: {}", e.getMessage(), e);

        List<Map<String, String>> validationErrors =
            e.getValueResults().stream()
                .map(
                    result ->
                        Map.of(
                            "field", result.getMethodParameter().getParameterName(),
                            "message", result.getResolvableErrors().getFirst().getDefaultMessage()))
                .toList();

        return new ResponseEntity<>(
            ApiResponse.error(ErrorType.METHOD_ARGUMENT_NOT_VALID, validationErrors),
            ErrorType.METHOD_ARGUMENT_NOT_VALID.getStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException e) {
        log.warn("Method argument type mismatch error occurred: {}", e.getMessage(), e);
        Map<String, String> errorDetails =
            Map.of(
                "parameter",
                e.getName(),
                "message",
                "invalid type: excpected " + e.getRequiredType().getSimpleName());
        return new ResponseEntity<>(
            ApiResponse.error(ErrorType.METHOD_ARGUMENT_NOT_VALID, List.of(errorDetails)),
            ErrorType.METHOD_ARGUMENT_NOT_VALID.getStatus());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNoResourceFoundException(NoResourceFoundException e) {
        log.warn("Resource not found error occurred: {}", e.getMessage(), e);
        return new ResponseEntity<>(
            ApiResponse.error(ErrorType.RESOURCE_NOT_FOUND), ErrorType.RESOURCE_NOT_FOUND.getStatus());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpRequestMethodNotSupportedException(
        HttpRequestMethodNotSupportedException e) {
        log.warn("Method not allowed error occurred: {}", e.getMessage(), e);
        return new ResponseEntity<>(
            ApiResponse.error(ErrorType.METHOD_NOT_ALLOWED), ErrorType.METHOD_NOT_ALLOWED.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return new ResponseEntity<>(
            ApiResponse.error(ErrorType.DEFAULT_ERROR), ErrorType.DEFAULT_ERROR.getStatus());
    }
}