package com.jb.CouponProjectSpring;

import com.jb.CouponProjectSpring.Util.Art;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CouponProjectSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponProjectSpringApplication.class, args);
		System.out.println(Art.localhost);
	}

}
