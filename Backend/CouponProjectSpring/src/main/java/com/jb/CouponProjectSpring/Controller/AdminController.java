package com.jb.CouponProjectSpring.Controller;

import com.jb.CouponProjectSpring.Beans.Company;
import com.jb.CouponProjectSpring.Beans.Customer;
import com.jb.CouponProjectSpring.Beans.UserDetails;
import com.jb.CouponProjectSpring.Enums.ClientType;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CompanyAlreadyExistException;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CompanyNotFoundException;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CustomerAlreadyExistException;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CustomerNotFoundException;
import com.jb.CouponProjectSpring.Exceptions.LoginErrorException;
import com.jb.CouponProjectSpring.Login.LoginManager;
import com.jb.CouponProjectSpring.Service.AdminService;
import com.jb.CouponProjectSpring.Service.ClientService;
import com.jb.CouponProjectSpring.Util.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * a Controller class that incorporates all of the methods accessible to a registered Administrator
 * The controller class is responsible for processing incoming REST API requests, preparing a model, and returning the view to be rendered as a response.
 */


@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class AdminController {

    private AdminService adminService = null;
    //private ClientService clientService;
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
            adminService = (AdminService) loginManager.login(userDetails.getEmail(), userDetails.getPassword(),
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
     * create a new company and sends it to be insert into the system database
     *
     * @param company variable
     * @param token  generates our key
     * @throws CompanyAlreadyExistException this exception will be thrown when company name is already exists in the system
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     * @return  ResponseEntity- ok or error
     */
    @PostMapping("addCompany")
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company)
            throws CompanyAlreadyExistException {
        if (myJWT.validateToken(token)) {
            try {
                adminService.addCompany(company);
            } catch (CompanyAlreadyExistException e) {
                throw new CompanyAlreadyExistException(e.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body("company was added");
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * method to update a company personal info
     *
     * @param company variable
     * @throws CompanyNotFoundException this exception will be thrown when the company not exists in the system
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     * @param token  generates our key
     * @return  ResponseEntity- ok or error
     */

    @PostMapping("updateCompany")
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws CompanyNotFoundException {
        if (myJWT.validateToken(token)) {
            try {
                adminService.updateCompany(company);
            } catch (CompanyNotFoundException e) {
                throw new CompanyNotFoundException(e.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body("company was update");
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * method to delete a company and all of its coupons from the database tables
     *
     * @param id variable
     * @throws CompanyNotFoundException this exception will be thrown when the company not exists in the system
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * DeleteMapping - Annotation for mapping HTTP DELETE requests onto specific handler methods.
     * @param token  generates our key
     * @return  ResponseEntity- ok or error
     */

    @DeleteMapping("deleteCompany/{id}")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws CompanyNotFoundException {
        if (myJWT.validateToken(token)) {
            try {
                adminService.deleteCompanyById(id);
            } catch (CompanyNotFoundException e) {
                throw new CompanyNotFoundException(e.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body("company was deleted");
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * returns all companies info
     *
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     * @param token  generates our key
     */


    @PostMapping("companies/all")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) {
        if (myJWT.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body(adminService.getAllCompanies());
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * gets an companyID and returns one company info
     * @param token  generates our key
     * @param id variable
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */

    @PostMapping("company/{id}")
    public ResponseEntity<?> getOneCompanyById(@RequestHeader(name = "Authorization") String token, @PathVariable int id) {
        if (myJWT.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body(adminService.getOneCompanyById(id));
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * create a new customer and sends it to be insert into the system database
     * @param token  generates our key
     * @param customer variable
     * @throws CustomerAlreadyExistException this exception will be thrown when customer is already exists in the system
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     * @return  ResponseEntity- ok or error
     */

    @PostMapping("addCustomer")
    public ResponseEntity<?> addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws CustomerAlreadyExistException {
        if (myJWT.validateToken(token)) {
            try {
                adminService.addCustomer(customer);
            } catch (CustomerAlreadyExistException e) {
                throw new CustomerAlreadyExistException(e.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body("customer was added");
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * method to update a customer personal info
     * @param token  generates our key
     * @param customer variable
     * @throws CustomerNotFoundException this exception will be thrown when customer is not exists in the system
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */

    @PostMapping("updateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws CustomerNotFoundException {
        if (myJWT.validateToken(token)) {
            try {
                adminService.updateCustomer(customer);
            } catch (CustomerNotFoundException e) {
                throw new CustomerNotFoundException(e.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body("customer was updated");
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }


    /**
     * method to delete a customer and all of its purchased history of coupons from the database tables
     * @param token  generates our key
     * @param id variable
     * @throws CustomerNotFoundException this exception will be thrown when customer is not exists in the system
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * DeleteMapping - Annotation for mapping HTTP DELETE requests onto specific handler methods.
     */


    @DeleteMapping("deleteCustomer/{id}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws CustomerNotFoundException {
        if (myJWT.validateToken(token)) {
            try {
                adminService.deleteCustomerById(id);
            } catch (CustomerNotFoundException e) {
                throw new CustomerNotFoundException(e.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body("customer was deleted");
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }


    /**
     * returns all of the registered Customers
     * @param token  generates our key
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */


    @PostMapping("customers/all")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String token) {
        if (myJWT.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body(adminService.getAllCustomers());
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * gets an ID and returns the one customer info
     * @param token  generates our key
     * @param id variable
     * @return  ResponseEntity- ok or error
     * ResponseEntity -Extension of HttpEntity that adds an HttpStatus status code. Used in RestTemplate as well as in @Controller methods.
     * PostMapping - Annotation for mapping HTTP POST requests onto specific handler methods.
     */


    @PostMapping("customer/{id}")
    public ResponseEntity<?> getOneCustomerById(@RequestHeader(name = "Authorization") String token, @PathVariable int id) {
        if (myJWT.validateToken(token)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(token))
                    .body(adminService.getOneCustomerById(id));
        }
        return new ResponseEntity<>("invalid Token", HttpStatus.UNAUTHORIZED);
    }


}


