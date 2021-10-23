package com.jb.CouponProjectSpring.CLR;

import com.jb.CouponProjectSpring.Beans.Company;
import com.jb.CouponProjectSpring.Beans.Coupon;
import com.jb.CouponProjectSpring.Beans.Customer;
import com.jb.CouponProjectSpring.Enums.Category;
import com.jb.CouponProjectSpring.Enums.ClientType;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CompanyAlreadyExistException;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CompanyNotFoundException;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CustomerAlreadyExistException;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CustomerNotFoundException;
import com.jb.CouponProjectSpring.Login.LoginManager;
import com.jb.CouponProjectSpring.Service.AdminService;
import com.jb.CouponProjectSpring.Util.ColorPrint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;

//@Component
@Order(1)
@RequiredArgsConstructor
public class TestAdmin implements CommandLineRunner {
    @Autowired
    private LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(ColorPrint.ANSI_GREEN + "ADMIN TEST" + ColorPrint.ANSI_RESET);
        // Dummy Objects
        Coupon coupon1 = Coupon.builder()
                .companyID(1)
                .category(Category.electricity)
                .title("battery")
                .description("batteries AAA")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2021-09-3"))
                .amount(500)
                .price(99.9)
                .image("image1")
                .build();

        Coupon coupon2 = Coupon.builder()
                .companyID(1)
                .category(Category.food)
                .title("organic juice")
                .description("orange juice")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2021-09-1"))
                .amount(40)
                .price(10)
                .image("image2")
                .build();

        Coupon coupon3 = Coupon.builder()
                .companyID(2)
                .category(Category.food)
                .title("cola drinks")
                .description("coca cola drinks")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2021-08-10"))
                .amount(220)
                .price(40.5)
                .image("image3")
                .build();

        Coupon coupon4 = Coupon.builder()
                .companyID(2)
                .category(Category.vacation)
                .title("usa")
                .description("vacation in NY")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2021-10-7"))
                .amount(10)
                .price(1500)
                .image("image4")
                .build();

        Coupon coupon5 = Coupon.builder()
                .companyID(3)
                .category(Category.restaurant)
                .title("elit factory")
                .description("visit elit factory")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2021-09-01"))
                .amount(50)
                .price(230)
                .image("image5")
                .build();

        Coupon coupon6 = Coupon.builder()
                .companyID(3)
                .category(Category.food)
                .title("milk chocolate")
                .description("white chocolate")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2021-08-10"))
                .amount(1000)
                .price(29.9)
                .image("image6")
                .build();

        Company company1 = Company.builder()
                .name("super Pharm")
                .email("superPharm@gmail.com")
                .password("3455")
                .coupons(Arrays.asList(coupon1, coupon2))
                .build();

        Company company2 = Company.builder()
                .name("cocaCola")
                .email("cocacola@gmail.com")
                .password("5678")
                .coupons(Arrays.asList(coupon3, coupon4))
                .build();

        Company company3 = Company.builder()
                .name("elit")
                .email("elit@gmail.com")
                .password("8890")
                .coupons(Arrays.asList(coupon5, coupon6))
                .build();

        Company company3update = Company.builder()
                .id(3)
                .name("elita")
                .email("elit99@gmail.com")
                .password("88910")
                .coupons(Arrays.asList(coupon5, coupon6))
                .build();

        // Login - Failed
        AdminService adminService = null;
        System.out.println(ColorPrint.ANSI_BLUE + "Administrator try to login..." + ColorPrint.ANSI_RESET);
        try {
            adminService = (AdminService) loginManager.login("admin@admin.com", "adminWrong", ClientType.administrator);
            //System.out.println(adminService);
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "LOGIN Failed" + ColorPrint.ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------");


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
        // Add Company
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to add a company..." + ColorPrint.ANSI_RESET);
        try {
            adminService.addCompany(company1);
            adminService.addCompany(company2);
            adminService.addCompany(company3);
        } catch (CompanyAlreadyExistException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Companies was added successfully!" + ColorPrint.ANSI_RESET);
        System.out.println("-------------------------------------------------");
        // Add Company - Failed - Name already exists
        try {
            adminService.addCompany(company1);
        } catch (CompanyAlreadyExistException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("---------------------------------------------------");
        Company company1update = Company.builder()
                .name("super Pharm")
                .email("superPharm@gmail.com")
                .password("3455")
                .coupons(Arrays.asList(coupon1, coupon2))
                .build();
        company1update.setName("Temp Company Name");

        // Add Company - Failed - Email already exists
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to add company with email that already exist " + ColorPrint.ANSI_RESET);
        try {
            adminService.addCompany(company1update);
        } catch (CompanyAlreadyExistException e) {
            System.out.println(e.getMessage());
            company1update.setName("super Pharm");
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Add Company - Failed - Email already exists" + ColorPrint.ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // Update Company - Failed - try to update company that not exist
        System.out.println(ColorPrint.ANSI_BLUE + "Try to update company that not exist" + ColorPrint.ANSI_RESET);
        try {
            company1update.setId(10);
            adminService.updateCompany(company1update);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Failed to update the ID" + ColorPrint.ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        company1update.setId(1);

        // Update Company - Failed - Cannot update Company Name
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to update Company Name" + ColorPrint.ANSI_RESET);
        try {
            company1update.setName("Temp Company Name");
            adminService.updateCompany(company1update);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(adminService.getOneCompanyById(company1update.getId()));
        System.out.println(ColorPrint.ANSI_BLUE + "Failed to update the Name" + ColorPrint.ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // Get All Companies
        System.out.println(ColorPrint.ANSI_BLUE + "Printing all companies..." + ColorPrint.ANSI_RESET);
        System.out.println(adminService.getAllCompanies());
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // Update Company - SUCCESS
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to update Company Password" + ColorPrint.ANSI_RESET);
        adminService.updateCompany(company3update);
        System.out.println(ColorPrint.ANSI_BLUE + "Password wad update successfully" + ColorPrint.ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.println(adminService.getOneCompanyById(company3.getId()));
        System.out.println(adminService.getOneCompanyById(company3update.getId()));

        // Delete Company
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to delete a company: " + ColorPrint.ANSI_RESET);
        try {
            adminService.deleteCompanyById(3);
        } catch (CompanyNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "elit has been deleted successfully!" + ColorPrint.ANSI_RESET);

        System.out.println(ColorPrint.ANSI_BLUE + "Printing all companies...\n " + ColorPrint.ANSI_RESET + adminService.getAllCompanies());
        System.out.println("----------------------------------------------------------------------------------------------------------------");


        // Get One Company
        System.out.println(ColorPrint.ANSI_BLUE + "Printing one company..." + ColorPrint.ANSI_RESET);
        System.out.println(adminService.getOneCompanyById(2));
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // Dummy Objects
        Customer customer1 = Customer.builder()
                .email("noa@gmail.com")
                .firstName("noa")
                .lastName("barmatz")
                .password("1234")
                .coupons(Arrays.asList(coupon1, coupon2, coupon3))
                .build();

        Customer customer2 = Customer.builder()
                .email("elad@gmail.com")
                .firstName("elad")
                .lastName("barmatz")
                .password("5678")
                .build();

        Customer customer3 = Customer.builder()
                .email("tamara@gmail.com")
                .firstName("tamara")
                .lastName("barmatz")
                .password("2020")
                .build();

        Customer customer4 = Customer.builder()
                .email("dolev@gmail.com")
                .firstName("dolev")
                .lastName("barmatz")
                .password("1991")
                .build();

        Customer customer2update = Customer.builder()
                .id(2)
                .email("eladbarm@gmail.com")
                .firstName("elad")
                .lastName("barmatz")
                .password("56789")
                .build();

        // Add Customer
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to add a customers..." + ColorPrint.ANSI_RESET);
        try {
            adminService.addCustomer(customer1);
            adminService.addCustomer(customer2);
            adminService.addCustomer(customer3);
            adminService.addCustomer(customer4);
        } catch (CustomerAlreadyExistException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ColorPrint.ANSI_BLUE + "Customers was added successfully" + ColorPrint.ANSI_RESET);

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // Add Customer - Failed - Email already exists
        System.out.printf(ColorPrint.ANSI_BLUE + "Preparing to add a customer: '%s %s' \n" + ColorPrint.ANSI_RESET
                , customer1.getFirstName(), customer1.getLastName());

        try {
            adminService.addCustomer(customer1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // Update Customer

        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to update a customer...\n" + ColorPrint.ANSI_RESET);

        adminService.updateCustomer(customer2update);

        try {
            adminService.updateCustomer(customer2update);
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.printf(ColorPrint.ANSI_BLUE + "Customer '%s %s' has been updated successfully!\n" + ColorPrint.ANSI_RESET, customer2update.getFirstName(), customer2update.getLastName());

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // Update Customer - Failed - try to update Customer that not exist
        System.out.println(ColorPrint.ANSI_BLUE + "Try to update Customer that not exist" + ColorPrint.ANSI_RESET);

        try {
            customer2update.setId(4);
            adminService.updateCustomer(customer2update);
        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        System.out.println(ColorPrint.ANSI_BLUE + "Update Failed - customer ID doesn't found " + ColorPrint.ANSI_RESET);

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // Delete Customer
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to delete the customer" + ColorPrint.ANSI_RESET);
        try {
            adminService.deleteCustomerById(2);
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Customer has been deleted successfully!" + ColorPrint.ANSI_RESET);

        // Get All Customers
        System.out.println(ColorPrint.ANSI_BLUE + "Printing all customers...\n" + ColorPrint.ANSI_RESET + adminService.getAllCustomers());
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // Get One Customer
        System.out.println(ColorPrint.ANSI_BLUE + "Printing one customer...\n" + ColorPrint.ANSI_RESET);
        System.out.println(adminService.getOneCustomerById(1));
        System.out.println("----------------------------------------------------------------------------------------------------------------");
    }
}
