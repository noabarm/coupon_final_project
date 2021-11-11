package com.jb.CouponProjectSpring.Exceptions.CustomerExceptions;
/**
 * Exception - points on of a attempt to purchase a expired coupon by the customer
 * may happen when trying to purchase a expired coupon
 */
public class CouponExpiredException extends Exception {
    public CouponExpiredException() {
        super("date of coupon expired");
    }
    /**
     * @param message variable
     */
    public CouponExpiredException(String message) {
        super(message);

    }
}
