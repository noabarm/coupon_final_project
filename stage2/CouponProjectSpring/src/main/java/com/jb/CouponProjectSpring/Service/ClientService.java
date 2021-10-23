package com.jb.CouponProjectSpring.Service;

import com.jb.CouponProjectSpring.Exceptions.LoginErrorException;
import com.jb.CouponProjectSpring.Repositories.CompanyRepository;
import com.jb.CouponProjectSpring.Repositories.CouponRepository;
import com.jb.CouponProjectSpring.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * An abstract class describing a Service that will be returned when a
 * CouponSystem Client of any type logs in.
 */
public abstract class ClientService {
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected CouponRepository couponRepository;
    @Autowired
    protected CompanyRepository companyRepository;

    /**
     * @param email    variable
     * @param password variable
     * @throws LoginErrorException -points on a failed attempt to log into the system, may happen when trying to log into the system
     * @return login
     */
    public abstract boolean login(String email, String password) throws LoginErrorException;

}
