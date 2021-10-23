package com.jb.CouponProjectSpring.Advice;

import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CompanyAlreadyExistException;
import com.jb.CouponProjectSpring.Exceptions.LoginErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Login Rest Exception Class - it is handled with exception,When an annotated exception is thrown from a controller method,
 * it will automatically cause the appropriate HTTP response to be returned with the specified status-code.
 */

@RestController
@ControllerAdvice
public class LoginRestException {

    /**
     * Exception - points on a failed attempt to log into the system
     * may happen when trying to log into the system
     * @param e Exception
     * @return ErrorDetail
     */

    @ExceptionHandler(value = {LoginErrorException.class})
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ErrorDetail handleLoginExp(Exception e){
        return new ErrorDetail("Login error", e.getMessage());
    }

}
