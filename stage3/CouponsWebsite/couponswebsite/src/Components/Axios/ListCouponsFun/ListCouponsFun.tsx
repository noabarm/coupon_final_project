import SingleCouponFun from "../../mainComponent/singleCouponFun/singleCouponFun";
import CouponDetails from "../../model/CouponDetails";
import "./ListCouponsFun.css";

interface ListCouponsFunStateProps {
	arrayCouponDetails:CouponDetails[]
}

function ListCouponsFun(props: ListCouponsFunStateProps): JSX.Element {
 
    return (
        <div className="ListCouponsFun">
			{props.arrayCouponDetails.map(item=><SingleCouponFun 
            key={item.id}
            couponDetails={item}             
             />)}
        </div>
    );
}

export default ListCouponsFun;
