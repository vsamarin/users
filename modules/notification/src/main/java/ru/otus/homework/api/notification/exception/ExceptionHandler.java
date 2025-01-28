package ru.otus.homework.api.notification.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ru.otus.homework.api.notification.dto.Error;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class, Throwable.class})
    public ResponseEntity<?> internalServerError(Exception e) {
        return processException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> unprocessableEntity(NotFoundException e) {
        return processException(e, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({HandlerMethodValidationException.class})
    public ResponseEntity<?> validationError(HandlerMethodValidationException e) {
        return processException(e);
    }


    private ResponseEntity<?> processException(HandlerMethodValidationException e) {
        String message;
        if (Objects.nonNull(e.getDetailMessageArguments()) && ArrayUtils.isNotEmpty(e.getDetailMessageArguments())) {
            message = e.getDetailMessageArguments()[0].toString();
        } else {
            message = e.getLocalizedMessage();
        }

        log.error(message, e);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                headers,
                HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> processException(Exception e, HttpStatus status) {
        log.error(e.getLocalizedMessage(), e);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new Error(status.value(), e.getMessage()), headers, status);
    }


}
