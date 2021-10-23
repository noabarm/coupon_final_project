package com.jb.CouponProjectSpring.Beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jb.CouponProjectSpring.Enums.Category;
import lombok.*;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * A Class for depicting a Coupon in the Coupon System
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * C`TOR for Coupon Containing all parameters
     *
     * @param id variable
     * @param companyID variable
     * @param category variable
     * @param title variable
     * @param description variable
     * @param startDate variable
     * @param endDate variable
     * @param amount variable
     * @param price variable
     * @param image variable
     * @param customers variable
     */
    private int id;
    @Column(updatable = false)
    private int companyID;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;


    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Customer.class)
    @JoinTable(name = "customer_coupons",
            joinColumns = {@JoinColumn(name = "coupon_id")},
            inverseJoinColumns = {@JoinColumn(name = "customer_id")})
    @ToString.Exclude()
    @JsonIgnore
    private List<Customer> customers;

}
