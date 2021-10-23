import CouponDetails from "./CouponDetails";

class CustomerDetails{
    id: number =0;
    firstName: string ="";
    lastName: string ="";
    email: string = "";
    password: string = "";
    coupons: CouponDetails[] =[];
}
export default CustomerDetails;

