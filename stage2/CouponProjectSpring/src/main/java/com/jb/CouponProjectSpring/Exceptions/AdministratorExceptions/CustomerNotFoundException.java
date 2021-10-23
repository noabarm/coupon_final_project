package com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions;
/**
 * Exception - points on a failed attempt to get customer information
 */
public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException() {
        super("Customer Not Found!");

    }
    /**
     * @param message variable
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
