package com.jb.CouponProjectSpring.Exceptions;
/**
 * Exception - points on a failed attempt to log into the system
 * may happen when trying to log into the system
 */
public class LoginErrorException extends Exception{
    /**
     *
     */
    public LoginErrorException() {
        super("Can't Login!");

    }

    /**
     * @param message variable
     */
    public LoginErrorException(String message) {
        super(message);
    }
}
