package br.com.askufcg.handler;

import br.com.askufcg.exceptions.BadRequestException;
import br.com.askufcg.exceptions.NoContentException;
import br.com.askufcg.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.BindException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDetails> handlerBadRequestException(BadRequestException bre) {
        return makeExceptionDetails(bre, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerNotFoundException(NotFoundException nfe) {
        return makeExceptionDetails(nfe, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ExceptionDetails> handlerNotFoundException(NoContentException nce) {
        return makeExceptionDetails(nce, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetails> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        String message = fieldErrors.get(0).getDefaultMessage();
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .message(message)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ExceptionDetails> makeExceptionDetails(RuntimeException ex, HttpStatus statusCode) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .message(ex.getMessage())
                        .status(statusCode.value())
                        .build(), statusCode);
    }
}
