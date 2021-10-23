package com.jb.CouponProjectSpring.CLR;

import com.jb.CouponProjectSpring.Enums.ClientType;
import com.jb.CouponProjectSpring.Exceptions.CompanyExceptions.CouponNotFoundException;
import com.jb.CouponProjectSpring.Login.LoginManager;
import com.jb.CouponProjectSpring.Service.CompanyService;
import com.jb.CouponProjectSpring.Util.ColorPrint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
@Order(4)
@RequiredArgsConstructor
public class TestCompany2 implements CommandLineRunner {
    @Autowired
    private LoginManager loginManager;


    @Override
    public void run(String... args) throws Exception {
        CompanyService companyService = null;
        System.out.println(ColorPrint.ANSI_BLUE + "Company try to login..." + ColorPrint.ANSI_RESET);
        try {
            companyService = (CompanyService) loginManager.login("superPharm@gmail.com", "3455", ClientType.company);
            //System.out.println(companyService);

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        System.out.println(ColorPrint.ANSI_BLUE + "delete coupon..." + ColorPrint.ANSI_RESET);
        try {
            companyService.deleteCouponById(2);
        } catch (CouponNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Delete Successfully" + ColorPrint.ANSI_RESET);

        System.out.println("------------------------------------------------------------");
    }
}
