package com.jb.CouponProjectSpring.Advice;

import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CompanyAlreadyExistException;
import com.jb.CouponProjectSpring.Exceptions.CompanyExceptions.CouponAlreadyRegisteredException;
import com.jb.CouponProjectSpring.Exceptions.CompanyExceptions.CouponNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Company Rest Exception Class - it is handled with exception,When an annotated exception is thrown from a controller method,
 * it will automatically cause the appropriate HTTP response to be returned with the specified status-code.
 */
@RestController
@ControllerAdvice
public class CompanyRestException {

    /**
     * Exception - points on duplication of attempt to create a new coupon name  at the same company
     * may happen when trying to create and insert a new coupon into the database
     *
     * @param e Exception
     * @return ErrorDetail
     */

    @ExceptionHandler(value = {CouponAlreadyRegisteredException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCouponExistExp(Exception e) {
        return new ErrorDetail("Coupon Exist error", e.getMessage());
    }

    /**
     * Exception - points on a failed attempt to get a coupon by ID
     * @param e Exception
     * @return ErrorDetail
     */

    @ExceptionHandler(value = {CouponNotFoundException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCouponNotFoundExistExp(Exception e) {
        return new ErrorDetail("Coupon not found error", e.getMessage());
    }


}
