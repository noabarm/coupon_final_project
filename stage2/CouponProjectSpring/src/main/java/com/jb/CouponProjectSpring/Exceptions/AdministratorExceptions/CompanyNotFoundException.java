package com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions;
/**
 * Exception - points on a failed attempt to get non exist company information
 */
public class CompanyNotFoundException extends Exception{

    public CompanyNotFoundException() {
        super("Company Not Found!");

    }

    /**
     * @param message variable
     */
    public CompanyNotFoundException(String message) {
        super(message);

    }
}
