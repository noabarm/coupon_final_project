package com.jb.CouponProjectSpring.Thread;

import com.jb.CouponProjectSpring.Repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Calendar;

/**
 * A Class that run and delete coupons their date were expired
 */
@Component
@EnableAsync //for enabling async task
@EnableScheduling //enable scheduling
public class CouponExpirationDailyJob {

    /**
     * method that run and delete coupons their date were expired
     */

    @Autowired
    private CouponRepository couponRepository;

    @Async
    //@Scheduled(fixedRate = 1000 * 60 * 60 * 24) // every 24 hours
    //@Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Jerusalem")
    public void myThread() {
        System.out.println("thread start");
        couponRepository.deleteByEndDateBefore(new Date(Calendar.getInstance().getTime().getTime()));
        System.out.println("expired coupons were deleted");

    }
}
