package com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions;
/**
 * Exception - points on duplication of a company name\company email attempt
 * may happen when trying to create and insert a new company into the database
 */
public class CompanyAlreadyExistException extends Exception {
    public CompanyAlreadyExistException() {
        super(" Company name or email already exist");
    }

    /**
     * @param message variable
     */
    public CompanyAlreadyExistException(String message) {
        super(message);
    }
}
