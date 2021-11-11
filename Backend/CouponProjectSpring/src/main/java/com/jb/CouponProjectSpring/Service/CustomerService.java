package com.jb.CouponProjectSpring.Service;

import com.jb.CouponProjectSpring.Beans.Coupon;
import com.jb.CouponProjectSpring.Beans.Customer;
import com.jb.CouponProjectSpring.Enums.Category;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CustomerNotFoundException;
import com.jb.CouponProjectSpring.Exceptions.CustomerExceptions.CouponAlreadyPurchasedException;
import com.jb.CouponProjectSpring.Exceptions.CustomerExceptions.CouponExpiredException;
import com.jb.CouponProjectSpring.Exceptions.CustomerExceptions.CouponSoldOutException;
import com.jb.CouponProjectSpring.Util.ColorPrint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


/**
 * a Service class that incorporates all of the methods accessible to a registered customer
 */

@Service
@RequiredArgsConstructor
public class CustomerService extends ClientService {
    private int customerID;

    /**
     * login method to check if the email and password matches and authorize as Customer
     *
     * @param email    variable
     * @param password variable
     * @return true if login succeed false otherwise
     */
    @Override
    public boolean login(String email, String password) {
        System.out.println("customer login " + this.customerID);
        Customer customer = customerRepository.findByEmailAndPassword(email, password);
        if (customer != null) {
            System.out.println("customer login success. customer id :" + customer.getId());
            this.customerID = customer.getId();
            return true;
        } else {
            return false;
        }
    }

    /**
     * purchase a coupon for the logged customer
     *
     * @param coupon variable
     * @throws CouponSoldOutException          this exception will be thrown when customer try to purchase coupon that sold out
     * @throws CouponExpiredException          this exception will be thrown when customer try to purchase coupon that expired
     * @throws CouponAlreadyPurchasedException this exception will be thrown when customer try to purchase coupon that already purchase
     */
    public void purchaseCoupon(Coupon coupon) throws CouponSoldOutException, CouponAlreadyPurchasedException, CouponExpiredException {
        Coupon dbCoupon = couponRepository.findById(coupon.getId());
        if (dbCoupon.getEndDate().before(new Date(Calendar.getInstance().getTime().getTime()))) {
            System.out.println("date of coupon expired");
            throw new  CouponExpiredException();
        }
        List<Integer> couponsId = customerRepository.findById(this.customerID).getCoupons().stream().map(Coupon::getId)
                .collect(Collectors.toList());
        for (int couponId : couponsId) {
            if (couponId == dbCoupon.getId()) {
                System.out.println("you already purchase this coupon");
                throw new CouponAlreadyPurchasedException();
            }
        }
        if (dbCoupon.getAmount() == 0) {
            System.out.println("there no coupon left");
            throw new CouponSoldOutException();

        }
        Customer tempCustomer = customerRepository.findById(customerID);
        dbCoupon.getCustomers().add(tempCustomer);
        dbCoupon.setAmount(dbCoupon.getAmount() - 1);
        couponRepository.saveAndFlush(dbCoupon);

    }

    /**
     * returns all purchased coupons of the logged customer
     *
     * @return coupons list
     */
    public List<Coupon> getAllPurchaseCoupon() {
        return customerRepository.findById(this.customerID).getCoupons();
    }

    /**
     * returns all purchased coupons of the logged customer by category
     *
     * @param category variable
     * @return coupons list
     */
    public List<Coupon> getAllCouponsByCategory(Category category) {
        List<Coupon> customerCoupons = getAllPurchaseCoupon();
        List<Coupon> categoryCoupons = new ArrayList<>();
        for (Coupon item : customerCoupons) {
            if (item.getCategory().equals(category)) {
                categoryCoupons.add(item);
            }
        }
        return categoryCoupons;
    }

    /**
     * returns all purchased coupons of the logged customer below a certain price.
     *
     * @param maxPrice variable
     * @return coupons list
     */
    public List<Coupon> getAllPurchaseCouponByMaxPrice(double maxPrice) {
        List<Coupon> customerCoupons = getAllPurchaseCoupon();
        List<Coupon> categoryCoupons = new ArrayList<>();
        for (Coupon item : customerCoupons) {
            if (item.getPrice() <= maxPrice) {
                categoryCoupons.add(item);
            }
        }
        return categoryCoupons;
    }

    /**
     * Get all the info of the customer
     *
     * @return customer details
     * @throws CustomerNotFoundException this exception will be thrown when the customer is not exists in the system
     */
    public Customer getCustomerDetails() throws CustomerNotFoundException {
        return customerRepository.findById(this.customerID);
    }

}

