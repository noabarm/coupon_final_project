package com.jb.CouponProjectSpring.CLR;

import com.jb.CouponProjectSpring.Service.UserService;
import com.jb.CouponProjectSpring.Util.ColorPrint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
@Order(6)
@RequiredArgsConstructor
public class TestUser implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Override
    public void run(String... args) throws Exception {
        System.out.println(ColorPrint.ANSI_PURPLE + "userTest " + ColorPrint.ANSI_RESET);
        System.out.println(userService.getAllCoupons());
        System.out.println(userService.getOneCoupon(8));

    }
}
