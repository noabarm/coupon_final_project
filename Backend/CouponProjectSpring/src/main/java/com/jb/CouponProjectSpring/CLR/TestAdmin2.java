package com.jb.CouponProjectSpring.CLR;

import com.jb.CouponProjectSpring.Enums.ClientType;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CompanyNotFoundException;
import com.jb.CouponProjectSpring.Login.LoginManager;
import com.jb.CouponProjectSpring.Service.AdminService;
import com.jb.CouponProjectSpring.Util.ColorPrint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
@Order(5)
@RequiredArgsConstructor
public class TestAdmin2 implements CommandLineRunner {
    @Autowired
    private LoginManager loginManager;
    @Override
    public void run(String... args) throws Exception {
        AdminService adminService = null;
        // LOGIN - SUCCESS
        System.out.println(ColorPrint.ANSI_BLUE + "Administrator try to login..." + ColorPrint.ANSI_RESET);
        try {
            adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.administrator);
            //System.out.println(adminService);
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "LOGIN SUCCESS" + ColorPrint.ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to delete a company: " + ColorPrint.ANSI_RESET);
        try {
            adminService.deleteCompanyById(2);
        } catch (CompanyNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "elit has been deleted successfully!" + ColorPrint.ANSI_RESET);

        adminService.deleteCustomerById(3);

    }
}
