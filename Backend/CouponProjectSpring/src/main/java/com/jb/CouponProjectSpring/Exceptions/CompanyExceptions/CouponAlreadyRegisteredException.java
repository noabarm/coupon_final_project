package com.jb.CouponProjectSpring.Exceptions.CompanyExceptions;

/**
 * Exception - points on duplication of attempt to create a new coupon name  at the same company
 * may happen when trying to create and insert a new coupon into the database
 */
public class CouponAlreadyRegisteredException extends Exception {
    public CouponAlreadyRegisteredException() {

        super("There is a coupon registered in the system with the same name");

    }

    /**
     * @param message variable
     */
    public CouponAlreadyRegisteredException(String message) {
        super(message);
    }
}
