package com.jb.CouponProjectSpring.Exceptions.CompanyExceptions;
/**
 * Exception - points on a failed attempt to get a coupon by ID
 */
public class CouponNotFoundException extends Exception{
    public CouponNotFoundException() {
        super("Coupon Not Found!");

    }

    /**
     * @param message variable
     */
    public CouponNotFoundException(String message) {
        super(message);

    }
}
