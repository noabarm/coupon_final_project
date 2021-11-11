package com.jb.CouponProjectSpring.Service;

import com.jb.CouponProjectSpring.Beans.Company;
import com.jb.CouponProjectSpring.Beans.Coupon;
import com.jb.CouponProjectSpring.Beans.Customer;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CompanyAlreadyExistException;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CompanyNotFoundException;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CustomerAlreadyExistException;
import com.jb.CouponProjectSpring.Exceptions.AdministratorExceptions.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * a Service class that incorporates all of the methods accessible to a registered Administrator
 */

@Service
@RequiredArgsConstructor
public class AdminService extends ClientService {

    /**
     * login method to check if the email and password matches and authorize as Administrator
     *
     * @param email    variable
     * @param password variable
     * @return true if login succeed false otherwise
     */

    public boolean login(String email, String password) {
        if (email.equals("admin@admin.com") && password.equals("admin")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * create a new company and sends it to be insert into the system database
     *
     * @param company variable
     * @throws CompanyAlreadyExistException this exception will be thrown when company name is already exists in the system
     */

    public void addCompany(Company company) throws CompanyAlreadyExistException {
        if (companyRepository.findByName(company.getName()) != null ||
                companyRepository.findByEmail(company.getEmail()) != null) {
            throw new CompanyAlreadyExistException();
        }
        companyRepository.save(company);
    }

    /**
     * method to update a company personal info
     *
     * @param company variable
     * @throws CompanyNotFoundException this exception will be thrown when the company not exists in the system
     */

    public void updateCompany(Company company) throws CompanyNotFoundException {
        Company updateCompany = companyRepository.findById(company.getId());
        if (updateCompany == null) {
            throw new CompanyNotFoundException();
        }
        updateCompany.setEmail(company.getEmail());
        updateCompany.setPassword(company.getPassword());
        companyRepository.saveAndFlush(updateCompany);

    }

    /**
     * method to delete a company and all of its coupons from the database tables
     *
     * @param companyid variable
     * @throws CompanyNotFoundException this exception will be thrown when the company not exists in the system
     */

    public void deleteCompanyById(int companyid) throws CompanyNotFoundException {
        if (!companyRepository.existsById(companyid)) {
            throw new CompanyNotFoundException();
        }
        companyRepository.deleteById(companyid);
    }

    /**
     * returns all companies info
     *
     * @return companies list
     */
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    /**
     * gets an companyID and returns one company info
     *
     * @param companyID variable
     * @return company
     */

    public Company getOneCompanyById(int companyID) {
        return companyRepository.findById(companyID);
    }

    /**
     * create a new customer and sends it to be insert into the system database
     *
     * @param customer variable
     * @throws CustomerAlreadyExistException this exception will be thrown when customer is already exists in the system
     */

    public void addCustomer(Customer customer) throws CustomerAlreadyExistException {
        if (customerRepository.findByEmail(customer.getEmail()) != null) {
            throw new CustomerAlreadyExistException();
        }
        customerRepository.save(customer);
    }

    /**
     * method to update a customer personal info
     *
     * @param customer variable
     * @throws CustomerNotFoundException this exception will be thrown when customer is not exists in the system
     */

    public void updateCustomer(Customer customer) throws CustomerNotFoundException {
        Customer updateCustomer = customerRepository.findById(customer.getId());
        if (updateCustomer == null) {
            throw new CustomerNotFoundException();
        }
        updateCustomer.setFirstName(customer.getFirstName());
        updateCustomer.setLastName(customer.getLastName());
        updateCustomer.setEmail(customer.getEmail());
        updateCustomer.setPassword(customer.getPassword());
        customerRepository.saveAndFlush(updateCustomer);
    }

    /**
     * method to delete a customer and all of its purchased history of coupons from the database tables
     *
     * @param customerID variable
     * @throws CustomerNotFoundException this exception will be thrown when customer is not exists in the system
     */
    public void deleteCustomerById(int customerID) throws CustomerNotFoundException {
        if (!customerRepository.existsById(customerID)) {
            throw new CustomerNotFoundException();
        }
        Customer tempCustomer = customerRepository.findById(customerID);
        for (Coupon coupon : customerRepository.findById(customerID).getCoupons()) {
            Optional<Customer> toRemove = coupon.getCustomers().stream().filter(f -> f.getId() == tempCustomer.getId()).findAny();
            coupon.getCustomers().remove(toRemove.get());
            couponRepository.saveAndFlush(coupon);
        }
        customerRepository.deleteById(customerID);
    }

    /**
     * returns all of the registered Customers
     *
     * @return customer list
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * gets an ID and returns the one customer info
     *
     * @param customerID variable
     * @return customer
     */
    public Customer getOneCustomerById(int customerID) {
        return customerRepository.findById(customerID);
    }
}
