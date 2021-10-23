package com.jb.CouponProjectSpring.Beans;

import com.jb.CouponProjectSpring.Enums.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

/**
 * @param email variable
 * @param password variable
 * @param clientType variable
 */
public class UserDetails {

    private String email;
    private String password;
    private ClientType clientType;
}
