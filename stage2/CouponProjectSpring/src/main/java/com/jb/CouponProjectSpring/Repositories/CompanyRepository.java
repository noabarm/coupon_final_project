package com.jb.CouponProjectSpring.Repositories;

import com.jb.CouponProjectSpring.Beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
    Company findById(int id);
    Company findByName(String name);
    Company findByEmail(String email);
    Company findByEmailAndPassword(String email,String password);
}
