package com.jb.CouponProjectSpring.CLR;

import com.jb.CouponProjectSpring.Beans.Company;
import com.jb.CouponProjectSpring.Beans.Coupon;
import com.jb.CouponProjectSpring.Beans.Customer;
import com.jb.CouponProjectSpring.Enums.Category;
import com.jb.CouponProjectSpring.Enums.ClientType;
import com.jb.CouponProjectSpring.Exceptions.CustomerExceptions.CouponAlreadyPurchasedException;
import com.jb.CouponProjectSpring.Exceptions.CustomerExceptions.CouponExpiredException;
import com.jb.CouponProjectSpring.Exceptions.CustomerExceptions.CouponSoldOutException;
import com.jb.CouponProjectSpring.Login.LoginManager;
import com.jb.CouponProjectSpring.Service.AdminService;
import com.jb.CouponProjectSpring.Repositories.CompanyRepository;
import com.jb.CouponProjectSpring.Repositories.CouponRepository;
import com.jb.CouponProjectSpring.Repositories.CustomerRepository;
import com.jb.CouponProjectSpring.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;

@Component
@Order(1)
@RequiredArgsConstructor
public class TestDB implements CommandLineRunner {
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    private final AdminService adminFacade;
    @Autowired
    private LoginManager loginManager;
    @Override
    public void run(String... args) throws Exception {

        Coupon coupon1c1 = Coupon.builder()
                .companyID(1)
                .category(Category.food)
                .title("Krispy Kreme Doughnut")
                .description("Our mission is to make the most awesome doughnuts on the planet every single day." +
                        " eGift card valid towards menu and merchandise.")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2022-11-3"))
                .amount(500)
                .price(65)
                .image("donuts.jpg")
                .build();

        Coupon coupon2c1 = Coupon.builder()
                .companyID(1)
                .category(Category.food)
                .title("Liquid Fusion Cafe")
                .description("Dine at Liquid Fusion Cafe and collect $5 Groupon Bucks to use on a future Groupon purchase")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2022-12-1"))
                .amount(120)
                .price(12)
                .image("coffee3.jpg")
                .build();

        Coupon coupon3c1 = Coupon.builder()
                .companyID(1)
                .category(Category.food)
                .title("Cold Creamery- Lockport")
                .description("Two Ice Cream Cups or One 6-inch Round Ice Cream Cake at Cold Stone Creamery")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2022-12-10"))
                .amount(220)
                .price(33)
                .image("iceCream.jpg")
                .build();

        Coupon coupon4c1 = Coupon.builder()
                .companyID(2)
                .category(Category.vacation)
                .title("Downtown Grand Hotel")
                .description("Stay for two in a deluxe king room, deluxe double-queen room, superior king room," +
                        " or superior double-queen room")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2022-12-7"))
                .amount(15)
                .price(3000)
                .image("building.jpg")
                .build();

        Coupon coupon5c1 = Coupon.builder()
                .companyID(3)
                .category(Category.restaurant)
                .title("Caffe Streets")
                .description("Dine at Caffe Streets and collect $5 Groupon Bucks to use on a future Groupon purchase")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2022-11-01"))
                .amount(50)
                .price(120)
                .image("coffee1.jpg")
                .build();


        Coupon coupon1c2 = Coupon.builder()
                .companyID(2)
                .category(Category.electricity)
                .title("Apple iMac 21.5")
                .description("Apple iMac 21.5in 2.7GHz Core i5 (ME086LL/A) All In One Desktop, 8GB or 16GB Memory, 1TB Hard Drive.")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2022-11-3"))
                .amount(40)
                .price(3200)
                .image("apple.jpg")
                .build();

        Coupon coupon2c2 = Coupon.builder()
                .companyID(2)
                .category(Category.electricity)
                .title("Silicone Sport Band")
                .description("This durable and smooth silicone band fits any Apple Watch for a snug, comfortable fit" +
                        " during exercise or everyday wear")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2022-12-1"))
                .amount(120)
                .price(199)
                .image("break.jpg")
                .build();

        Coupon coupon3c2 = Coupon.builder()
                .companyID(2)
                .category(Category.electricity)
                .title("Bluetooth Wireless Earbuds")
                .description("L Club INC 2021Bluetooth 5.0 Wireless Earbuds for iPhone, Android, Wireless Headphones" +
                        " Built-in Microphone, Waterproof Sports Bluetooth Earbuds for Home and Gym")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2022-12-10"))
                .amount(450)
                .price(148)
                .image("earphones.jpg")
                .build();

        Coupon coupon4c2 = Coupon.builder()
                .companyID(2)
                .category(Category.vacation)
                .title("Hilton Niagara Falls")
                .description("Stay for two in a city-view compact king room, city-view double-queen room," +
                        " falls-view king Jacuzzi room, or falls-view double-queen whirlpool room")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2022-11-7"))
                .amount(24)
                .price(3980)
                .image("waterfalls.jpg")
                .build();

        Coupon coupon5c2 = Coupon.builder()
                .companyID(2)
                .category(Category.sport)
                .title("Body And Brain Yoga")
                .description("Tai Chi exercises emphasize energy circulation, breathing, and balance," +
                        " along with mindfulness and concentration.")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2022-11-01"))
                .amount(50)
                .price(120)
                .image("yoga.jpg")
                .build();

        Coupon coupon1c3 = Coupon.builder()
                .companyID(3)
                .category(Category.electricity)
                .title("Sonic Toothbrush")
                .description("The Sonic-FX Solo toothbrush is capable of over 33,000 strokes per minute," +
                        " is ultra-lightweight and rechargeable (lithium-ion battery), has twist and lock brush heads" +
                        " for quick and convenient changing and 3 different speeds.")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2022-11-3"))
                .amount(540)
                .price(126)
                .image("toothbrush.jpg")
                .build();

        Coupon coupon2c3 = Coupon.builder()
                .companyID(3)
                .category(Category.electricity)
                .title("Acoustic Beam Soundbar")
                .description("Samsung HW-Q67CT 7.1ch Acoustic Beam Soundbar w/ Wireless Rear Kit Refurbished")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2022-12-1"))
                .amount(120)
                .price(1780)
                .image("microphone.jpg")
                .build();

        Coupon coupon3c3 = Coupon.builder()
                .companyID(3)
                .category(Category.sport)
                .title("Portable Basketball")
                .description("Kids Youth Portable Basketball Hoop, 7'-10' Height Adjustable")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2022-12-10"))
                .amount(450)
                .price(148)
                .image("basketball.jpg")
                .build();

        Coupon coupon4c3 = Coupon.builder()
                .companyID(3)
                .category(Category.vacation)
                .title("Park Central Hotel New York")
                .description("Stay for two in a classic king room, classic two-double room, or premier double-queen room")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2022-11-7"))
                .amount(40)
                .price(4980)
                .image("usa.jpg")
                .build();

        Coupon coupon5c3 = Coupon.builder()
                .companyID(3)
                .category(Category.restaurant)
                .title("biga")
                .description("Professional restaurant offers a variety of food and drinks as well as special events" +
                        " including Star Wars trivia and dinner pairings")
                .startDate(new Date(Calendar.getInstance().getTime().getTime()))
                .endDate(Date.valueOf("2022-11-01"))
                .amount(670)
                .price(97)
                .image("coffee2.jpg")
                .build();




        Company company1 = Company.builder()
                .name("Amazon")
                .email("amazon.com")
                .password("3455")
                .coupons(Arrays.asList(coupon1c1,coupon2c1,coupon3c1,coupon4c1,coupon5c1))
                .build();

        Company company2 = Company.builder()
                .name("Coca Cola")
                .email("cocacola@gmail.com")
                .password("5678")
                .coupons(Arrays.asList(coupon1c2,coupon2c2,coupon3c2,coupon4c2,coupon5c2))
                .build();

        Company company3 = Company.builder()
                .name("Buy Me")
                .email("buyme@gmail.com")
                .password("8890")
                .coupons(Arrays.asList(coupon1c3,coupon2c3,coupon3c3,coupon4c3,coupon5c3))
                .build();

        companyRepository.saveAll(Arrays.asList(company1,company2,company3));

        System.out.println(companyRepository.findAll());

        Customer customer1 = Customer.builder()
                .firstName("noa")
                .lastName("barmatz")
                .password("1234")
                .email("noabar99@gmail.com")
                .coupons(Arrays.asList(coupon1c1,coupon2c2,coupon3c3,coupon4c1,coupon5c2))
                .build();

        System.out.println(customer1);

        customerRepository.save(customer1);

        System.out.println(customerRepository.findById(1));

        CustomerService customerService = null;
        customerService = (CustomerService) loginManager.login("noabar99@gmail.com", "1234", ClientType.customer);

        try {
            customerService.purchaseCoupon(coupon1c1);
            customerService.purchaseCoupon(coupon2c2);
            customerService.purchaseCoupon(coupon3c3);
            customerService.purchaseCoupon(coupon4c1);
            customerService.purchaseCoupon(coupon5c2);

        } catch (CouponSoldOutException | CouponAlreadyPurchasedException | CouponExpiredException e) {
            System.out.println(e.getMessage());
        }


    }
}
