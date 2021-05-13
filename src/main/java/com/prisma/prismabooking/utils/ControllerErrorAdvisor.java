package com.prisma.prismabooking.utils;

import com.google.gson.Gson;
import com.prisma.prismabooking.PrismaBookingApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerErrorAdvisor {
    Gson gson = PrismaBookingApplication.gson();

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex){
        return ResponseEntity.status(BadRequestException.class.getAnnotation(ResponseStatus.class).value()).body(gson.toJson(ex.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<Object> handleConflictException(ConflictException ex){
        return ResponseEntity.status(ConflictException.class.getAnnotation(ResponseStatus.class).value()).body(gson.toJson("ConflictException occurred: " +ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex){
        return ResponseEntity.status(NotFoundException.class.getAnnotation(ResponseStatus.class).value()).body(gson.toJson("NotFoundException occurred: " + ex.getMessage()));
    }


}
