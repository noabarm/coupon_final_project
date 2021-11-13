import jwtDecode from "jwt-decode";
import AuthenToken from "../../model/AuthenToken";
import CouponDetails from "../../model/CouponDetails";
import { couponUpdateAction } from "../../redux/CouponState";
import { customerCouponUpdateAction } from "../../redux/CustomerState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import notify from "../../utils/Notify";
import "./singleCouponFun.css";

interface SingleCouponFunProps {
    /*
	title:string,
    endDate:string,
    amount:number,
    price:number,
    image:string
    */
    couponDetails:CouponDetails;
}


function SingleCouponFun(props: SingleCouponFunProps): JSX.Element {
    const imgUrl = "https://backendlessappcontent.com/AE176C35-B9A0-07F6-FF0E-242CF8C99600/ACFDB970-70A6-45A1-9877-9716DEE3861D/files/img/"+props.couponDetails.image;
    const myUrl = globals.urls.customer + "purchaseCoupon";
    let decoded: AuthenToken;

  
    
    const handleClick =()=>{

        if(store.getState().authState.loginUser.token === ""){
            notify.error("You should sing up as customer first");
            return; 
        }else{
                decoded = jwtDecode(localStorage.getItem("token"));
                if (decoded.clientType !== "customer"){
                notify.error("You should sing up as customer first");
                return;
                }
            } 

        decoded = jwtDecode(localStorage.getItem("token"));
        if (decoded.clientType !== "customer"){
           notify.error("You should sing up as customer first");
           return;
        }

        jwtAxios.post(myUrl,props.couponDetails)
            .then((response)=>{
            store.dispatch(customerCouponUpdateAction());
            store.dispatch(couponUpdateAction(props.couponDetails));
            notify.success("coupon was purch successfully!");
            console.log(response);
        }).catch(error=>{
            console.log(error);
            notify.error(error.response.data.description)});
            
        /*
        store.dispatch(logoutUser());
        history.push("/openPage")
        */
    } 


    return (
        <div className="singleCouponFun couponBox">
			<img className ="imgCoupon" src={imgUrl} width="85%" height="47%"/>
			<hr/>
            <h3>{props.couponDetails.title}</h3>
            price: {props.couponDetails.price}$<br/>
            only {props.couponDetails.amount} left!<br/>
            end date: {props.couponDetails.endDate}<br/><br/>
            <input className="buyButton" type="button" value="buy" onClick={handleClick} />
        </div>
    );
}

export default SingleCouponFun;
