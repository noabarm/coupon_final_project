package com.jb.CouponProjectSpring.CLR;

import com.jb.CouponProjectSpring.Beans.Coupon;
import com.jb.CouponProjectSpring.Enums.Category;
import com.jb.CouponProjectSpring.Enums.ClientType;
import com.jb.CouponProjectSpring.Exceptions.CompanyExceptions.CouponAlreadyRegisteredException;
import com.jb.CouponProjectSpring.Exceptions.CompanyExceptions.CouponNotFoundException;
import com.jb.CouponProjectSpring.Login.LoginManager;
import com.jb.CouponProjectSpring.Service.CompanyService;
import com.jb.CouponProjectSpring.Util.ColorPrint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.sql.Date;
import java.util.Calendar;

//@Component
@Order(2)
@RequiredArgsConstructor
public class TestCompany implements CommandLineRunner {
    @Autowired
    private LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(ColorPrint.ANSI_GREEN + "COMPANY TEST" + ColorPrint.ANSI_RESET);
        CompanyService companyService = null;

        //define coupons
        Coupon coupon7 = Coupon.builder()
                .category(Category.restaurant)
                .title("elit factory")
                .description("visit elit factory")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2021-09-01"))
                .amount(50)
                .price(230)
                .image("image7")
                .build();

        Coupon coupon9 = Coupon.builder()
                .category(Category.restaurant)
                .title("elit factory trip")
                .description("visit elit factory")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2021-10-01"))
                .amount(50)
                .price(160)
                .image("image5")
                .build();

        Coupon coupon10 = Coupon.builder()
                .category(Category.food)
                .title("milk chocolate")
                .description("white chocolate")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2021-08-10"))
                .amount(0)
                .price(29.9)
                .image("image6")
                .build();

        Coupon coupon8 = Coupon.builder()
                .category(Category.food)
                .title("ice cream")
                .description("vanilla ice cream")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2021-09-1"))
                .amount(40)
                .price(10)
                .image("image8")
                .build();


        // LOGIN - Failed
        System.out.println(ColorPrint.ANSI_BLUE + "Company try to login..." + ColorPrint.ANSI_RESET);
        try {
            companyService = (CompanyService) loginManager.login("cocacola@gmail.com", "1000", ClientType.company);
            //System.out.println(companyService);

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "LOGIN Failed" + ColorPrint.ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // LOGIN - SUCCESS
        System.out.println(ColorPrint.ANSI_BLUE + "Company try to login..." + ColorPrint.ANSI_RESET);
        try {
            companyService = (CompanyService) loginManager.login("superPharm@gmail.com", "3455", ClientType.company);
            //System.out.println(companyService);

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // Add Coupons
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to add a coupons..." + ColorPrint.ANSI_RESET);
        try {
            companyService.addCoupon(coupon8);
            companyService.addCoupon(coupon9);
            companyService.addCoupon(coupon7);
            companyService.addCoupon(coupon10);
        } catch (CouponAlreadyRegisteredException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ColorPrint.ANSI_BLUE + "coupons were added successfully!\n" + ColorPrint.ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        //Add Coupons - Failed - Name already exists in this company
        System.out.println(ColorPrint.ANSI_BLUE + "try to add coupon with the same name and the same company id...\n" + ColorPrint.ANSI_RESET);
        try {
            companyService.addCoupon(coupon9);
        } catch (CouponAlreadyRegisteredException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        //Add Coupons - with same Name - different company
        System.out.println(ColorPrint.ANSI_BLUE + "try to add coupon with the same name and different company id...\n" + ColorPrint.ANSI_RESET);
        Coupon coupon13 = Coupon.builder()
                .category(Category.food)
                .title("cola drinks")
                .description("coca cola drinks")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2021-10-01"))
                .amount(500)
                .price(35)
                .image("image555")
                .build();

        try {
            companyService.addCoupon(coupon13);
        } catch (CouponAlreadyRegisteredException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "coupons were added successfully!" + ColorPrint.ANSI_RESET);

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        //update coupon amount
        System.out.println(ColorPrint.ANSI_BLUE + "update the amount of the coupon to 50..." + ColorPrint.ANSI_RESET);
        Coupon updateCoupon = companyService.getAllCompanyCoupons().get(1);
        System.out.println(updateCoupon);
        updateCoupon.setAmount(50);
        try {
            companyService.updateCoupon(updateCoupon);
        } catch (CouponNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("--------------------------------------------------------------");

        //try to update company id
        System.out.println(ColorPrint.ANSI_BLUE + "try to update company id.." + ColorPrint.ANSI_RESET);
        Coupon updateCoupon1 = companyService.getAllCompanyCoupons().get(3);
        updateCoupon1.setCompanyID(4);
        try {
            companyService.updateCoupon(updateCoupon1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("--------------------------------------------------------------------------------------");

        //delete coupon
        System.out.println(ColorPrint.ANSI_BLUE + "delete coupon..." + ColorPrint.ANSI_RESET);
        try {
            companyService.deleteCouponById(1);
        } catch (CouponNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Delete Successfully" + ColorPrint.ANSI_RESET);

        System.out.println("------------------------------------------------------------");

        //Get all company coupons
        System.out.println(ColorPrint.ANSI_BLUE + "Get all company coupons:" + ColorPrint.ANSI_RESET);
        try {
            System.out.println(companyService.getAllCompanyCoupons());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("-------------------------------------------------------------------------");

        //Get all company coupons by category

        System.out.println(ColorPrint.ANSI_BLUE + "Print all company coupons by restaurant category:" + ColorPrint.ANSI_RESET);
        try {
            System.out.println(companyService.getAllCouponsByCategory(Category.restaurant));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("---------------------------------------------------------------------------------------");

        //Get all company coupons by max price
        System.out.println(ColorPrint.ANSI_BLUE + "print all company coupons by max price 50:" + ColorPrint.ANSI_RESET);
        try {
            System.out.println(companyService.getAllCouponsUntilMaxPrice(50));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("-----------------------------------------------------------------------------------");

        //Get company details
        System.out.println(ColorPrint.ANSI_BLUE + "print get company details:" + ColorPrint.ANSI_RESET);
        System.out.println(companyService.getCompanyDetails());

        System.out.println("----------------------------------------------------------------------------------------------------------------");

    }
}
