package com.jb.CouponProjectSpring.Service;

import com.jb.CouponProjectSpring.Beans.Coupon;
import com.jb.CouponProjectSpring.Beans.Customer;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CustomerAlreadyExistException;
import com.jb.CouponProjectSpring.Exceptions.LoginErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService extends ClientService{
    @Override
    public boolean login(String email, String password) throws LoginErrorException {
        return false;
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Coupon getOneCoupon(int id){
        return couponRepository.findById(id);
    }

    public void addCustomer(Customer customer) throws CustomerAlreadyExistException {
        if (customerRepository.findByEmail(customer.getEmail()) != null) {
            throw new CustomerAlreadyExistException();
        }
        customerRepository.save(customer);
    }
}
