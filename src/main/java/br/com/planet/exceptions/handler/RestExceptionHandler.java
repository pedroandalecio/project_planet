package br.com.planet.exceptions.handler;

import br.com.planet.constants.ErrorCodes;
import br.com.planet.exceptions.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleExceptions(Exception e, WebRequest request) {
        logger.error("An unexpected error occur", e);
        Throwable cause, resultCause = e;
        while ((cause = resultCause.getCause()) != null && resultCause != cause) {
            resultCause = cause;
        }

        if (resultCause instanceof ConstraintViolationException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(ErrorCodes.VALIDATION_FAILED,
                    (((ConstraintViolationException) resultCause).getConstraintViolations()).iterator().next().getMessage()));
        }
        
        request.getDescription(false);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ErrorCodes.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Invalid Arguments ", ex);

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(ErrorCodes.INVALID_REQUEST, ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(EntityExistsException.class)
    protected ResponseEntity<Object> handleEntityExistsException(EntityExistsException ex) {
        logger.error("Entity exist ", ex);

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(ErrorCodes.VALIDATION_FAILED, ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex,
                                                             WebRequest request) {
        logger.error("Entity Not found", ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ErrorCodes.ENTITY_NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        logger.error("MethodArgumentNotValid ", ex);

        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = new ArrayList<>();
        fieldErrors.forEach(f ->
                errors.add(String.format("%s : %s", f.getField(), f.getDefaultMessage()))
        );

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(ErrorCodes.VALIDATION_FAILED, errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}
