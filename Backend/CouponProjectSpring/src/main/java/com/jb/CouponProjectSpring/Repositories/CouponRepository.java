package com.jb.CouponProjectSpring.Repositories;

import com.jb.CouponProjectSpring.Beans.Coupon;
import com.jb.CouponProjectSpring.Enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon,Integer> {
    Coupon findByTitleAndCompanyID(String title,int companyID);
    Coupon findById(int id);
    List<Coupon> findByCategoryAndCompanyID(Category category, int companyID);
    List<Coupon> findByCompanyIDAndPriceLessThanEqual(int companyID,double price);
    @Transactional
    void deleteByEndDateBefore(Date endDate);

}
