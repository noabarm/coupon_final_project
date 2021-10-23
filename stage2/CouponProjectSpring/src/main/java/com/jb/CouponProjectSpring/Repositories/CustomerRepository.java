package com.jb.CouponProjectSpring.Repositories;

import com.jb.CouponProjectSpring.Beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findById(int id);
    Customer findByEmail(String email);
    Customer findByEmailAndPassword(String email, String password);


}
