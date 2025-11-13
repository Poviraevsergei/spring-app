package by.tms.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<HttpStatusCode> usernameExistsException(UsernameExistsException e) {
        System.out.println("Username exists: " + e.getUsername());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<HttpStatusCode> sqlException(SQLException e) {
        System.out.println("SQL exception: " + e.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpStatusCode> userNotFoundException(UserNotFoundException e) {
        System.out.println("UserNotFoundException: " + e.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<HttpStatusCode> validationException(ValidationException e) {
        System.out.println("ValidationException: " + e.getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
