package com.jb.CouponProjectSpring.Exceptions.CustomerExceptions;
/**
 * Exception - points on a purchase attempt of coupon that has been sold out
 * may happen when trying to purchase a coupon
 */
public class CouponSoldOutException extends Exception{
    public CouponSoldOutException() {
        super("SOLD OUT!");

    }

    /**
     * @param message variable
     */
    public CouponSoldOutException(String message) {
        super(message);
    }
}
