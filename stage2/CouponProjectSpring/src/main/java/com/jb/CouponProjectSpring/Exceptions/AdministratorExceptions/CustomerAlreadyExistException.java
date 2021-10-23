package com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions;
/**
 * Exception - points on duplication of a Customer name\email attempt
 * may happen when trying to create and insert a new Customer into the database
 */
public class CustomerAlreadyExistException extends Exception{
    public CustomerAlreadyExistException() {
        super("Name or email already registered!");

    }

    /**
     * @param message variable
     */
    public CustomerAlreadyExistException(String message) {
        super(message);
    }
}
