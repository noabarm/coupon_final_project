package com.jb.CouponProjectSpring.Advice;

import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CompanyAlreadyExistException;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CompanyNotFoundException;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CustomerAlreadyExistException;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Admin Rest Exception Class - it is handled with exception,When an annotated exception is thrown from a controller method,
 * it will automatically cause the appropriate HTTP response to be returned with the specified status-code.
 */

@RestController
@ControllerAdvice
public class AdminRestException {

    /**
     * Exception - points on duplication of a company name\company email attempt
     * may happen when trying to create and insert a new company into the database
     *
     * @param e Exception
     * @return ErrorDetail
     */
    @ExceptionHandler(value = {CompanyAlreadyExistException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCompanyExistExp(Exception e) {
        return new ErrorDetail("Company Exist error", e.getMessage());
    }

    /**
     * Exception - points on a failed attempt to get non exist company information
     *
     * @param e Exception
     * @return ErrorDetail
     */

    @ExceptionHandler(value = {CompanyNotFoundException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCompanyNotFoundExp(Exception e) {
        return new ErrorDetail("Company not found error", e.getMessage());
    }

    /**
     * Exception - points on duplication of a Customer name\email attempt
     * may happen when trying to create and insert a new Customer into the database
     *
     * @param e Exception
     * @return ErrorDetail
     */
    @ExceptionHandler(value = {CustomerAlreadyExistException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCustomerExistExp(Exception e) {
        return new ErrorDetail("Customer Exist error", e.getMessage());
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
        return new ErrorDetail("Customer update error", e.getMessage());
    }

}
