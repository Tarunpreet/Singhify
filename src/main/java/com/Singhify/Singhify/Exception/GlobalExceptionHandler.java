package com.Singhify.Singhify.Exception;

import com.Singhify.Singhify.APIResponses.ErrorStruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exception)
    {
        ErrorStruct error=new ErrorStruct(
                exception.getMessage(),
                404,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error.toString(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EntityAlreadyCreatedException.class)
    public ResponseEntity<String> handleEntityAlreadyCreatedException(EntityAlreadyCreatedException exception)
    {
        ErrorStruct error=new ErrorStruct(
                exception.getMessage(),
                400,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error.toString(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PaginationParameterNotValid.class)
    public ResponseEntity<String> handlePaginationParameterNotValidException(PaginationParameterNotValid exception)
    {
        ErrorStruct error=new ErrorStruct(
                exception.getMessage(),
                400,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error.toString(),HttpStatus.BAD_REQUEST);
    }




}
