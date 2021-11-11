package com.jb.CouponProjectSpring.Beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.List;

/**
 * A class depicting a Customer.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * A  C`TOR for a customer, taking in all parameters.
     *
     * @param id variable
     * @param firstName variable
     * @param lastName variable
     * @param email variable
     * @param password variable
     * @param coupons variable
     */
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Singular
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "customers")
    //@JsonIgnore
    private List<Coupon> coupons;

}
