package tech.keshav.payment.paymentproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserErrorHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public UserAlreadyExistsException handleCustomException(UserAlreadyExistsException ce) {
        System.out.println("Error Occured: " + ce);
        return ce;
    }

    @ExceptionHandler(AuthenticationFailed.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AuthenticationFailed handleInvalidUser(AuthenticationFailed err) {
        System.out.println("Error Occured: " + err);
        return err;
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public UnAuthorizedException handleUnAuthorized(UnAuthorizedException exception) {
        System.out.println("Error Occured: " + exception);
        return exception;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Exception defaultHandler(Exception exception) {
        System.out.println("Exception Occured: " + exception);
        return exception;
    }
}
