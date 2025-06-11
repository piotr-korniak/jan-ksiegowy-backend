package pl.janksiegowy.backend.shared.web;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.janksiegowy.backend.authorization.user.UserAlreadyExistsException;
import pl.janksiegowy.backend.entity.EntityAlreadyExistsException;
import pl.janksiegowy.backend.shared.Util;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler( UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists( UserAlreadyExistsException ex) {
        return ResponseEntity.status( HttpStatus.CONFLICT).body(
                ErrorResponse.create()
                        .code( "USER_ALREADY_EXISTS")
                        .message( ex.getMessage()));
    }

    @ExceptionHandler( ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation( ValidationException ex) {
        return ResponseEntity.badRequest().body(
                ErrorResponse.create()
                        .code( "VALIDATION_ERROR")
                        .status( HttpStatus.BAD_REQUEST.value())
                        .error( "Bad Request")
                        .message( ex.getMessage()));
    }

    @ExceptionHandler( EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists( EntityAlreadyExistsException ex) {
        return ResponseEntity.status( HttpStatus.CONFLICT).body(
                ErrorResponse.create()
                        .code( "ENTITY_ALREADY_EXISTS")
                        .message( ex.getMessage()));
    }

    @ExceptionHandler( MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<ErrorField> fields= new ArrayList<>();

        ex.getBindingResult().getAllErrors()
                .forEach( objectError -> {
                    if( objectError instanceof FieldError error ){
                        fields.add( new ErrorField( error.getField(), error.getDefaultMessage()));
                    }});

        return new ResponseEntity<ErrorResponse>(
                ErrorResponse.create()
                        .code( "VALIDATION_ERROR")
                        .status( HttpStatus.BAD_REQUEST.value())
                        .error( "Bad Request")
                        .message( "Validation failed")
                        .timestamp( LocalDateTime.now())
                        .path( request.getRequestURI())
                        .fields( fields),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidFormat( HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if( cause instanceof InvalidFormatException formatEx) {
            String fieldName = getFieldName(formatEx);
            Class<?> targetType = formatEx.getTargetType();

            if (targetType.isEnum()) {
                String allowedValues = Stream.of(targetType.getEnumConstants())
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));

                return ResponseEntity.badRequest().body(
                        ErrorResponse.create()
                                .code( "VALIDATION_ERROR")
                                .status( HttpStatus.BAD_REQUEST.value())
                                .error( "Bad Request")
                                .message( "Validation failed")
                                .timestamp( LocalDateTime.now())
                                .fields( List.of(
                                        new ErrorField( fieldName, "Invalid value. Allowed: "+ allowedValues))
                                ));
            }
        }
        return ResponseEntity.badRequest().body( "Malformed JSON");
    }

    private String getFieldName(InvalidFormatException ex) {
        return ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .filter(Objects::nonNull)
                .reduce((first, second) -> second) // najgłębsze pole
                .orElse("unknown");
    }
}