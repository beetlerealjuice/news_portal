package com.example.news_portal.web.controller;

import com.example.news_portal.exceptions.*;
import com.example.news_portal.web.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(EntityNotFoundException ex) {
        log.error("Ошибка при попытке получить сущность", ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new com.example.news_portal.web.model.ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(UserNameIsPresentException.class)
    public ResponseEntity<ErrorResponse> isPresent(UserNameIsPresentException ex) {
        log.error("Ошибка при добавлении пользователя", ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new com.example.news_portal.web.model.ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(NotAccessibleException.class)
    public ResponseEntity<ErrorResponse> notAccessibleUser(NotAccessibleException ex) {
        log.error("Ошибка при попытке отредактировать новость или комментарий");

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new com.example.news_portal.web.model.ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(DublicateNewsTitleException.class)
    public ResponseEntity<ErrorResponse> dublicateNews(DublicateNewsTitleException ex) {
        log.error("Попытка сохранить новость с дублирующемся названием");

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new com.example.news_portal.web.model.ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation() {
        String errorMessage = "Ошибка: Данная категория уже существует.";
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> notValid(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        String errorMessage = String.join("; ", errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessage));

    }

    @ExceptionHandler(NotRightsException.class)
    public ResponseEntity<ErrorResponse> noRights(NotRightsException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new com.example.news_portal.web.model.ErrorResponse(ex.getLocalizedMessage()));
    }
}
