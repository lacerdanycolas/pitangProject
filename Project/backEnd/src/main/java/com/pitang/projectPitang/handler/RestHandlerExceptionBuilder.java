package com.pitang.projectPitang.handler;

import com.pitang.projectPitang.error.ResourceNotFoundException;
import com.pitang.projectPitang.error.ResourceNotFoundExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestHandlerExceptionBuilder {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException resourceNotFoudException) {
    ResourceNotFoundExceptionDetail resourceNotFoundDetails = ResourceNotFoundExceptionDetail.ResourceBuilder.builder()
      .title("O recurso não foi encontrado!")
      .detail(resourceNotFoudException.getMessage())
      .moment(new Date()
        .getTime())
      .status(HttpStatus.NOT_FOUND.value())
      .developerMessage(resourceNotFoudException.getClass().getName()).build();

    return new ResponseEntity<>(resourceNotFoundDetails, HttpStatus.NOT_FOUND);

  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<?> handlerNoSuchElementException(ResourceNotFoundException resourceNotFoudException) {
    ResourceNotFoundExceptionDetail resourceNotFoundDetails = ResourceNotFoundExceptionDetail.ResourceBuilder.builder()
      .title("O recurso não foi encontrado!")
      .detail(resourceNotFoudException.getMessage())
      .moment(new Date()
        .getTime())
      .status(HttpStatus.NOT_FOUND.value())
      .developerMessage(resourceNotFoudException.getClass().getName()).build();

    return new ResponseEntity<>(resourceNotFoundDetails, HttpStatus.NOT_FOUND);

  }

}
