class Globals{}

class DevelopmentGlobals extends Globals{
    public urls = {
        administrator : "http://localhost:8080/admin/",
        //administrator : "https://noa-coupon.herokuapp.com/admin/",
        company : "http://localhost:8080/company/",
        customer : "http://localhost:8080/customer/",
        guest : "http://localhost:8080/guest/",
        general : "/"
    }
}

class ProductionGlobals extends Globals{
    public urls = {
        administrator : "/admin/",
        company : "/company/",
        customer : "/customer/",
        guest : "/guest/",
        general : "/"
    }
}

const globals = process.env.NODE_ENV === "production" ? new ProductionGlobals() : new DevelopmentGlobals();

export default globals;
