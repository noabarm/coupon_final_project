package com.jb.CouponProjectSpring.Login;

import com.jb.CouponProjectSpring.Enums.ClientType;
import com.jb.CouponProjectSpring.Exceptions.LoginErrorException;
import com.jb.CouponProjectSpring.Service.AdminService;
import com.jb.CouponProjectSpring.Service.ClientService;
import com.jb.CouponProjectSpring.Service.CompanyService;
import com.jb.CouponProjectSpring.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {
    @Autowired
    protected AdminService adminService;

    @Autowired
    protected CompanyService companyService;

    @Autowired
    protected CustomerService customerService;

    /**
     * method login that check by email,password and client type if the user can enter to the system
     *
     * @param email      variable
     * @param password   variable
     * @param clientType variable
     * @return the correct user facade
     * @throws LoginErrorException this exception will be thrown when the client failed to login into the system
     */
    public ClientService login(String email, String password, ClientType clientType) throws LoginErrorException {
        String failedLoginMsg = "Login failed. Please verify email & password.";
        String loginSuccess = "Logged in as: ";

        switch (clientType) {
            case administrator:
                if (!adminService.login(email, password)) {
                    throw new LoginErrorException(failedLoginMsg);
                }
                System.out.printf(loginSuccess + "'%s'\n", email);
                return adminService;

            case company:
                if (!companyService.login(email, password)) {
                    throw new LoginErrorException(failedLoginMsg);
                }
                System.out.printf(loginSuccess + "'%s'\n", email);
                return companyService;

            case customer:
                if (!customerService.login(email, password)) {
                    throw new LoginErrorException(failedLoginMsg);
                }
                System.out.printf(loginSuccess + "'%s'\n", email);
                return customerService;

            default:
                return null;

        }
    }
}
