package com.jb.CouponProjectSpring.Service;

import com.jb.CouponProjectSpring.Beans.Company;
import com.jb.CouponProjectSpring.Beans.Coupon;
import com.jb.CouponProjectSpring.Beans.Customer;
import com.jb.CouponProjectSpring.Enums.Category;
import com.jb.CouponProjectSpring.Exceptions.CompanyExceptions.CouponAlreadyRegisteredException;
import com.jb.CouponProjectSpring.Exceptions.CompanyExceptions.CouponNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
/**
 * a  Service class that incorporates all of the methods accessible to a registered company
 */

@Service
@RequiredArgsConstructor
public class CompanyService extends ClientService {

    private int companyId;

    /**
     * login method to check if the email and password matches and authorize as Company
     *
     * @param email    variable
     * @param password variable
     * @return true if login succeed false otherwise
     */
    @Override
    public boolean login(String email, String password) {
        System.out.println("company login " + this.companyId);
        Company company = companyRepository.findByEmailAndPassword(email, password);
        if (company != null) {
            System.out.println("company login success. company id :" + company.getId());
            this.companyId = company.getId();
            return true;
        } else {
            return false;
        }
    }

    /**
     * create a new coupon of the logged company into the database
     *
     * @param coupon variable
     * @throws CouponAlreadyRegisteredException this exception will be thrown when coupon is already exists in the system
     */
    public void addCoupon(Coupon coupon) throws CouponAlreadyRegisteredException {
        if (couponRepository.findByTitleAndCompanyID(coupon.getTitle(), coupon.getCompanyID()) != null) {
            System.out.println("Coupon Already Registered");
            throw new CouponAlreadyRegisteredException();
        } else {
            Company tempCompany = companyRepository.findById(companyId);
            tempCompany.getCoupons().add(coupon);
            companyRepository.saveAndFlush(tempCompany);
        }
    }

    /**
     * Updates a coupon. Changes the data of the coupon on the database, to the coupon received in the method.
     *
     * @param coupon variable
     * @throws CouponNotFoundException this exception will be thrown when coupon is not exists in the system
     */
    public void updateCoupon(Coupon coupon) throws CouponNotFoundException {
        Coupon updateCoupon = couponRepository.findById(coupon.getId());
        if (updateCoupon == null) {
            throw new CouponNotFoundException();
        }
        updateCoupon.setAmount(coupon.getAmount());
        updateCoupon.setCategory(coupon.getCategory());
        updateCoupon.setDescription(coupon.getDescription());
        updateCoupon.setEndDate(coupon.getEndDate());
        updateCoupon.setTitle(coupon.getTitle());
        updateCoupon.setStartDate(coupon.getStartDate());
        updateCoupon.setPrice(coupon.getPrice());
        updateCoupon.setImage(coupon.getImage());
        couponRepository.saveAndFlush(updateCoupon);
    }

    @Transactional
    /**
     * Deletes A Coupon from the database. Both from all clients records and from the coupon list.
     *
     * @param couponID variable
     * @throws CouponNotFoundException this exception will be thrown when coupon is not exists in the system
     */
    public void deleteCouponById(int couponId) throws CouponNotFoundException {
        if (!couponRepository.existsById(couponId)) {
            throw new CouponNotFoundException();
        }
        Coupon coupon = couponRepository.findById(couponId);
        for (Customer customer : coupon.getCustomers()) {
            customer.getCoupons().remove(coupon);
        }
        couponRepository.deleteById(couponId);
    }

    /**
     * Get all the coupons of the company from the Database.
     *
     * @return List of Coupons.
     */
    public List<Coupon> getAllCompanyCoupons() {
        return companyRepository.findById(this.companyId).getCoupons();
    }

    /**
     * Get all the coupons of the company from the Database by category.
     *
     * @param category variable
     * @return List of Coupons.
     */
    public List<Coupon> getAllCouponsByCategory(Category category) {
        return couponRepository.findByCategoryAndCompanyID(category, this.companyId);
    }

    /**
     * Get all the coupons of the company from the Database below a certain price.
     *
     * @param maxPrice variable
     * @return List of Coupons.
     */
    public List<Coupon> getAllCouponsUntilMaxPrice(double maxPrice) {
        return couponRepository.findByCompanyIDAndPriceLessThanEqual(this.companyId, maxPrice);
    }

    /**
     * Get all the info of the company
     *
     * @return company
     */
    public Company getCompanyDetails() {
        return companyRepository.findById(this.companyId);
    }


}
