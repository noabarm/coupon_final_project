package com.jb.CouponProjectSpring.Beans;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.List;

/**
 * A Class for depicting a Company in the Coupon System.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * C`TOR for a company taking in all parameters
     *
     * @param id variable
     * @param name variable
     * @param email variable
     * @param password variable
     * @param coupons variable
     */
    private int id;
    private String name;
    private String email;
    private String password;

    @Singular
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "companyid")
    //@JsonIgnore
    private List<Coupon> coupons;
}
