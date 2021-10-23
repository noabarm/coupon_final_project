package com.jb.CouponProjectSpring.Advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

/**
 * @param error variable
 * @param description variable
 */
public class ErrorDetail {

    private String error;
    private String description;

}
