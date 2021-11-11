package com.jb.CouponProjectSpring.Controller;

import com.jb.CouponProjectSpring.Beans.Company;
import com.jb.CouponProjectSpring.Beans.Customer;
import com.jb.CouponProjectSpring.Beans.UserDetails;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CompanyAlreadyExistException;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CustomerAlreadyExistException;
import com.jb.CouponProjectSpring.Exceptions.LoginErrorException;
import com.jb.CouponProjectSpring.Login.LoginManager;
import com.jb.CouponProjectSpring.Service.AdminService;
import com.jb.CouponProjectSpring.Service.ClientService;
import com.jb.CouponProjectSpring.Service.UserService;
import com.jb.CouponProjectSpring.Util.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("guest")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class GuestController {
    private ClientService clientService;

    @Autowired
    public UserService userService;
    private final JWTutil myJWT;
    private final LoginManager loginManager;


    @GetMapping("coupons/all")
    public ResponseEntity<?> getAllCoupons() {
        return new ResponseEntity<>(userService.getAllCoupons(), HttpStatus.OK);
    }

    @PostMapping("coupon/{id}")
    public ResponseEntity<?> getOneCouponById(@PathVariable int id) {
        return new ResponseEntity<>(userService.getOneCoupon(id), HttpStatus.ACCEPTED);
    }

    @PostMapping("user/register")
    public ResponseEntity<?> register(@RequestBody Customer customer) throws CustomerAlreadyExistException {
        try {
            userService.addCustomer(customer);
        } catch (CustomerAlreadyExistException e) {
            throw new CustomerAlreadyExistException(e.getMessage());
        }
        return ResponseEntity.ok()
                .body("Customer was added");
    }
}
