package com.jb.CouponProjectSpring.Advice;


import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CustomerNotFoundException;
import com.jb.CouponProjectSpring.Exceptions.CompanyExceptions.CouponAlreadyRegisteredException;
import com.jb.CouponProjectSpring.Exceptions.CompanyExceptions.CouponNotFoundException;
import com.jb.CouponProjectSpring.Exceptions.CustomerExceptions.CouponAlreadyPurchasedException;
import com.jb.CouponProjectSpring.Exceptions.CustomerExceptions.CouponExpiredException;
import com.jb.CouponProjectSpring.Exceptions.CustomerExceptions.CouponSoldOutException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Customer Rest Exception Class - it is handled with exception,When an annotated exception is thrown from a controller method,
 * it will automatically cause the appropriate HTTP response to be returned with the specified status-code.
 */
@RestController
@ControllerAdvice
public class CustomerRestException {


    /**
     * Exception - points on a purchase attempt of coupon that has been sold out
     * may happen when trying to purchase a coupon
     *
     * @param e Exception
     * @return ErrorDetail
     */


    @ExceptionHandler(value = {CouponSoldOutException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCouponSoldOutExp(Exception e) {
        return new ErrorDetail("Coupon sold out error", e.getMessage());
    }

    /**
     * Exception - points on of a repeated attempt to purchase a coupon by the same customer
     * may happen when trying to purchase a duplicate coupon
     *
     * @param e Exception
     * @return ErrorDetail
     */

    @ExceptionHandler(value = {CouponAlreadyPurchasedException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCouponAlreadyPurchaseExp(Exception e) {
        return new ErrorDetail("Coupon already purchase error", e.getMessage());
    }

    /**
     * Exception - points on of a attempt to purchase a expired coupon by the customer
     * may happen when trying to purchase a expired coupon
     *
     * @param e Exception
     * @return ErrorDetail
     */

    @ExceptionHandler(value = {CouponExpiredException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCouponExpiredExp(Exception e) {
        return new ErrorDetail("Coupon expired error", e.getMessage());
    }

    /**
     * Exception - points on a failed attempt to get customer information
     *
     * @param e Exception
     * @return ErrorDetail
     */

    @ExceptionHandler(value = {CustomerNotFoundException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCustomerNotFound(Exception e) {
        return new ErrorDetail("Customer not found error", e.getMessage());
    }


}
