package com.jb.CouponProjectSpring.Controller;

import com.jb.CouponProjectSpring.Beans.Company;
import com.jb.CouponProjectSpring.Beans.Coupon;
import com.jb.CouponProjectSpring.Beans.UserDetails;
import com.jb.CouponProjectSpring.Enums.Category;
import com.jb.CouponProjectSpring.Enums.ClientType;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CompanyAlreadyExistException;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CompanyNotFoundException;
import com.jb.CouponProjectSpring.Exceptions.CompanyExceptions.CouponAlreadyRegisteredException;
import com.jb.CouponProjectSpring.Exceptions.CompanyExceptions.CouponNotFoundException;
import com.jb.CouponProjectSpring.Exceptions.LoginErrorException;
import com.jb.CouponProjectSpring.Login.LoginManager;
import com.jb.CouponProjectSpring.Service.AdminService;
import com.jb.CouponProjectSpring.Service.CompanyService;
import com.jb.CouponProjectSpring.Util.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * a Controller class  that incorporates all of the methods accessible to a registered company
 * The controller class is responsible for processing incoming REST API requests, preparing a model, and returning the view to be rendered as a response.
 */

@RestController
@RequestMapping("company")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor


public class CompanyController {
    private CompanyService companyService = null;
    private final JWTutil myJWT;
    private final LoginManager loginManager;
    private static String token;


    /**
     * login method to check if the email and password matches and authorize as Company
     * @param userDetails variable
     * @return ResponseEntity -  true if login succeed false otherwise
     * @throws LoginErrorException this exception will be thrown when the client user try to login to the system
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */

    @PostMapping("login")
    private ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws LoginErrorException {
        try {
            companyService = (CompanyService) loginManager.login(userDetails.getEmail(), userDetails.getPassword(),
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
     * create a new coupon of the logged company into the database
     * @param token  generates our key
     * @param coupon variable
     * @throws CouponAlreadyRegisteredException this exception will be thrown when coupon is already exists in the system
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */

    @PostMapping("addCoupon")
    public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon)
            throws CouponAlreadyRegisteredException {
        if (myJWT.validateToken(token)) {
            try {
                companyService.addCoupon(coupon);
            } catch (CouponAlreadyRegisteredException e) {
                throw new CouponAlreadyRegisteredException(e.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body("coupon was added");
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }


    /**
     * Updates a coupon. Changes the data of the coupon on the database, to the coupon received in the method.
     * @param token  generates our key
     * @param coupon variable
     * @throws CouponNotFoundException this exception will be thrown when coupon is not exists in the system
    * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */


    @PostMapping("updateCoupon")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws CouponNotFoundException {
        if (myJWT.validateToken(token)) {
            try {
                companyService.updateCoupon(coupon);
            } catch (CouponNotFoundException e) {
                throw new CouponNotFoundException(e.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body("coupon was update");
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }


    /**
     * Deletes A Coupon from the database. Both from all clients records and from the coupon list.
     * @param token  generates our key
     * @param id variable
     * @throws CouponNotFoundException this exception will be thrown when coupon is not exists in the system
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * DeleteMapping - Annotation for mapping HTTP DELETE requests onto specific handler methods.
     */

    @DeleteMapping("deleteCoupon/{id}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws CouponNotFoundException {
        if (myJWT.validateToken(token)) {
            try {
                companyService.deleteCouponById(id);
            } catch (CouponNotFoundException e) {
                throw new CouponNotFoundException(e.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body("coupon was deleted");
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }


    /**
     * Get all the coupons of the company from the Database.
     * @param token  generates our key
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */

    @PostMapping("companyCoupon/all")
    public ResponseEntity<?> getAllCoupons(@RequestHeader(name = "Authorization") String token) {
        if (myJWT.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body(companyService.getAllCompanyCoupons());
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }


    /**
     * Get all the coupons of the company from the Database by category.
     * @param token  generates our key
     * @param category variable
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */

    @PostMapping("companyCoupon/category/{category}")
    public ResponseEntity<?> getAllCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable Category category)  {
        if (myJWT.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body(companyService.getAllCouponsByCategory(category));
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }


    /**
     * Get all the coupons of the company from the Database below a certain price.
     * @param token  generates our key
     * @param maxPrice variable
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */

    @PostMapping("companyCoupon/maxPrice/{maxPrice}")
    public ResponseEntity<?> getAllCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice)  {
        if (myJWT.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body(companyService.getAllCouponsUntilMaxPrice(maxPrice));
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Get all the info of the company
     * @param token  generates our key
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */

    @PostMapping("details")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token) {
        if (myJWT.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body(companyService.getCompanyDetails());
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }


}

