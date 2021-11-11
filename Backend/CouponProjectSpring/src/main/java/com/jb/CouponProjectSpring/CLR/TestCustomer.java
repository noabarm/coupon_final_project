package com.jb.CouponProjectSpring.CLR;

import com.jb.CouponProjectSpring.Beans.Coupon;
import com.jb.CouponProjectSpring.Enums.Category;
import com.jb.CouponProjectSpring.Enums.ClientType;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CustomerNotFoundException;
import com.jb.CouponProjectSpring.Exceptions.CustomerExceptions.CouponAlreadyPurchasedException;
import com.jb.CouponProjectSpring.Exceptions.CustomerExceptions.CouponExpiredException;
import com.jb.CouponProjectSpring.Exceptions.CustomerExceptions.CouponSoldOutException;
import com.jb.CouponProjectSpring.Login.LoginManager;
import com.jb.CouponProjectSpring.Service.CustomerService;
import com.jb.CouponProjectSpring.Repositories.CouponRepository;
import com.jb.CouponProjectSpring.Util.ColorPrint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.List;

//@Component
@Order(3)
@RequiredArgsConstructor
public class TestCustomer implements CommandLineRunner {
    @Autowired
    private LoginManager loginManager;
    private final CouponRepository couponRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(ColorPrint.ANSI_GREEN + "CUSTOMER TEST" + ColorPrint.ANSI_RESET);
        CustomerService customerService = null;
        // Login - Failed
        System.out.println(ColorPrint.ANSI_BLUE + "Customer try to login..." + ColorPrint.ANSI_RESET);
        try {
            customerService = (CustomerService) loginManager.login("maor@gmail.com", "1257", ClientType.customer);
            //System.out.println(customerService);

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "LOGIN Failed" + ColorPrint.ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // LOGIN - SUCCESS
        System.out.println(ColorPrint.ANSI_BLUE + "Customer try to login..." + ColorPrint.ANSI_RESET);
        try {
            customerService = (CustomerService) loginManager.login("tamara@gmail.com", "2020", ClientType.customer);
            //System.out.println(customerService);

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "LOGIN SUCCESS" + ColorPrint.ANSI_RESET);

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // COUPON PURCHASE - SUCCESS

        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to purchase a coupon..." + ColorPrint.ANSI_RESET);
        List<Coupon> allCouponInDB = couponRepository.findAll();
        //System.out.println(ColorPrint.ANSI_PURPLE + allCouponInDB + ColorPrint.ANSI_RESET);
        try {
            customerService.purchaseCoupon(allCouponInDB.get(0));
            customerService.purchaseCoupon(allCouponInDB.get(1));
            customerService.purchaseCoupon(allCouponInDB.get(2));
            customerService.purchaseCoupon(allCouponInDB.get(4));
            customerService.purchaseCoupon(allCouponInDB.get(5));

        } catch (CouponSoldOutException | CouponAlreadyPurchasedException | CouponExpiredException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "The purchase of the coupons was successful" + ColorPrint.ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // COUPON PURCHASE - Failed - Cant Purchase coupon more than once
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to purchase a coupon that the customer already purchase..." + ColorPrint.ANSI_RESET);
        try {
            customerService.purchaseCoupon(allCouponInDB.get(1));
        } catch (CouponSoldOutException | CouponAlreadyPurchasedException | CouponExpiredException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Purchase Failed" + ColorPrint.ANSI_RESET);

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // COUPON PURCHASE - Failed - Coupon is expired
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to purchase a coupon that the end date was past..." + ColorPrint.ANSI_RESET);
        try {
            customerService.purchaseCoupon(allCouponInDB.get(3));
        } catch (CouponSoldOutException | CouponAlreadyPurchasedException | CouponExpiredException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Purchase Failed" + ColorPrint.ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // COUPON PURCHASE - Failed - The amount of the Coupon is empty

        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to purchase a coupon with empty amount..." + ColorPrint.ANSI_RESET);
        try {
            customerService.purchaseCoupon(allCouponInDB.get(6));
        } catch (CouponSoldOutException | CouponExpiredException | CouponAlreadyPurchasedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Purchase Failed" + ColorPrint.ANSI_RESET);

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // GET ALL PURCHASE COUPONS
        System.out.println(ColorPrint.ANSI_BLUE + "Print all purchase Coupons: " + ColorPrint.ANSI_RESET);
        System.out.println(customerService.getAllPurchaseCoupon());
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        //Get all purchase coupons by category
        System.out.println(ColorPrint.ANSI_BLUE + "Print all coupon of food category = FOOD... " + ColorPrint.ANSI_RESET);
        System.out.println(customerService.getAllCouponsByCategory(Category.food));
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        //Get all purchase coupons by max price
        System.out.println(ColorPrint.ANSI_BLUE + "Print all coupon until max price 200 " + ColorPrint.ANSI_RESET);
        System.out.println(customerService.getAllPurchaseCouponByMaxPrice(200));
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        //print customer details
        System.out.println(ColorPrint.ANSI_BLUE + "Print customer details..." + ColorPrint.ANSI_RESET);
        try {
            System.out.println(customerService.getCustomerDetails());
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // LOGIN - SUCCESS
        System.out.println(ColorPrint.ANSI_BLUE + "Customer try to login..." + ColorPrint.ANSI_RESET);
        try {
            customerService = (CustomerService) loginManager.login("noa@gmail.com", "1234", ClientType.customer);
            System.out.println(customerService);

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "LOGIN SUCCESS" + ColorPrint.ANSI_RESET);

        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to purchase a coupon..." + ColorPrint.ANSI_RESET);

        try {
            customerService.purchaseCoupon(allCouponInDB.get(6));
            customerService.purchaseCoupon(allCouponInDB.get(7));
            customerService.purchaseCoupon(allCouponInDB.get(2));
            customerService.purchaseCoupon(allCouponInDB.get(4));
            customerService.purchaseCoupon(allCouponInDB.get(5));

        } catch (CouponSoldOutException | CouponAlreadyPurchasedException | CouponExpiredException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "The purchase of the coupons was successful" + ColorPrint.ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------");



        System.out.println(ColorPrint.ANSI_YELLOW + "FINISHED TEST" + ColorPrint.ANSI_RESET);
        /*
        customerFacade.login("tamara@gmail.com","2020");

        System.out.println("-----------------------------------------------");

        List<Coupon> allCouponInDB = couponRepository.findAll();
        System.out.println(allCouponInDB);

        customerFacade.purchaseCoupon(allCouponInDB.get(0));
        System.out.println("-----------------------------------------------");
        // COUPON PURCHASE - Failed - Cant Purchase coupon more than once
        customerFacade.purchaseCoupon(allCouponInDB.get(0));

        // COUPON PURCHASE - Failed - Coupon is expired
        customerFacade.purchaseCoupon(allCouponInDB.get(4));

        // COUPON PURCHASE - Failed - The amount of the Coupon is empty
        customerFacade.purchaseCoupon(allCouponInDB.get(5));

        // COUPON PURCHASE - SUCCESS
        customerFacade.purchaseCoupon(allCouponInDB.get(1));
        customerFacade.purchaseCoupon(allCouponInDB.get(2));
        customerFacade.purchaseCoupon(allCouponInDB.get(3));

        System.out.println("--------------------------------------------------------");
        //Get all purchase coupons
        System.out.println(customerFacade.getAllPurchaseCoupon());

        System.out.println("--------------------------------------------------------");
        //Get all purchase coupons by category
        System.out.println(customerFacade.getAllCouponsByCategory(Category.food));

        System.out.println("--------------------------------------------------------");
        //Get all purchase coupons by max price
        System.out.println(customerFacade.getAllPurchaseCouponByMaxPrice(300));

        System.out.println("-----------------------------------------------------");
        System.out.println("get customer details:");
        System.out.println(customerFacade.getCustomerDetails());

         */
    }
}
