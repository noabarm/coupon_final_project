package com.jb.CouponProjectSpring.Controller;


import com.jb.CouponProjectSpring.Beans.Coupon;
import com.jb.CouponProjectSpring.Beans.UserDetails;
import com.jb.CouponProjectSpring.Enums.Category;
import com.jb.CouponProjectSpring.Enums.ClientType;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CustomerNotFoundException;
import com.jb.CouponProjectSpring.Exceptions.CompanyExceptions.CouponAlreadyRegisteredException;
import com.jb.CouponProjectSpring.Exceptions.CompanyExceptions.CouponNotFoundException;
import com.jb.CouponProjectSpring.Exceptions.CustomerExceptions.CouponAlreadyPurchasedException;
import com.jb.CouponProjectSpring.Exceptions.CustomerExceptions.CouponExpiredException;
import com.jb.CouponProjectSpring.Exceptions.CustomerExceptions.CouponSoldOutException;
import com.jb.CouponProjectSpring.Exceptions.LoginErrorException;
import com.jb.CouponProjectSpring.Login.LoginManager;
import com.jb.CouponProjectSpring.Service.CompanyService;
import com.jb.CouponProjectSpring.Service.CustomerService;
import com.jb.CouponProjectSpring.Util.ColorPrint;
import com.jb.CouponProjectSpring.Util.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * a Controller class that incorporates all of the methods accessible to a registered customer
 */


@RestController
@RequestMapping("customer")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class CustomerController {
    private CustomerService customerService = null;
    private final JWTutil myJWT;
    private final LoginManager loginManager;
    private static String token;


    /**
     * login method to check if the email and password matches and authorize as Administrator
     *
     * @param userDetails variable
     * @return ResponseEntity -  true if login succeed false otherwise
     * @throws LoginErrorException this exception will be thrown when the client user try to login to the system
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */

    @PostMapping("login")
    private ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws LoginErrorException {
        try {
            customerService = (CustomerService) loginManager.login(userDetails.getEmail(), userDetails.getPassword(),
                    userDetails.getClientType());
            return ResponseEntity.ok(myJWT.generateToken(userDetails));
        } catch (LoginErrorException e) {
            throw new LoginErrorException(e.getMessage());
        }
    }


    /**
     * login method to check if the email and password matches and authorize as Administrator
     *
     * @param token variable
     * @return HttpHeaders -  A data structure representing HTTP request or response headers, mapping String header token to a list of String values
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     */

    private HttpHeaders getHeaders(String token) {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(myJWT.extractEmail(token));
        System.out.println(myJWT.extractAllClaims(token).get("clientType"));
        userDetails.setClientType(ClientType.valueOf((String) myJWT.extractAllClaims(token).get("clientType")));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", myJWT.generateToken(userDetails));
        return httpHeaders;

    }


    /**
     * purchase a coupon for the logged customer
     * @param token  generates our key
     * @param coupon variable
     * @throws CouponSoldOutException          this exception will be thrown when customer try to purchase coupon that sold out
     * @throws CouponExpiredException          this exception will be thrown when customer try to purchase coupon that expired
     * @throws CouponAlreadyPurchasedException this exception will be thrown when customer try to purchase coupon that already purchase
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */


    @PostMapping("purchaseCoupon")
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon)
            throws CouponSoldOutException, CouponAlreadyPurchasedException, CouponExpiredException {
        if (myJWT.validateToken(token)) {
            try {
                customerService.purchaseCoupon(coupon);
            } catch (CouponSoldOutException | CouponAlreadyPurchasedException | CouponExpiredException e) {
                throw new CouponAlreadyPurchasedException(e.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body("coupon was purchased");
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }


    /**
     * returns all purchased coupons of the logged customer
     * @param token  generates our key
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */


    @PostMapping("customerCoupon/all")
    public ResponseEntity<?> getAllPurchaseCoupon(@RequestHeader(name = "Authorization") String token) {
        if (myJWT.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body(customerService.getAllPurchaseCoupon());
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * returns all purchased coupons of the logged customer by category
     * @param token  generates our key
     * @param category variable
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */


    @PostMapping("customerCoupon/category/{category}")
    public ResponseEntity<?> getAllCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) {
        if (myJWT.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body(customerService.getAllCouponsByCategory(category));
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * returns all purchased coupons of the logged customer below a certain price.
     * @param token  generates our key
     * @param maxPrice variable
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */

    @PostMapping("customerCoupon/maxPrice/{maxPrice}")
    public ResponseEntity<?> getAllPurchaseCouponByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) {
        if (myJWT.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body(customerService.getAllPurchaseCouponByMaxPrice(maxPrice));
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }


    /**
     * Get all the info of the customer
     * @param token  generates our key
     * @return  ResponseEntity- ok or error
     * @throws CustomerNotFoundException this exception will be thrown when the customer is not exists in the system
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */

    @PostMapping("details")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name = "Authorization") String token) throws CustomerNotFoundException {
        if (myJWT.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body(customerService.getCustomerDetails());
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }


}





