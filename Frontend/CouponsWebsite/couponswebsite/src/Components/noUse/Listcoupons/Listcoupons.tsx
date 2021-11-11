import axios from "axios";
import { Component } from "react";
import CouponDetails from "../../model/CouponDetails";
import SingleCoupon from "../SingleCoupon/SingleCoupon";
import "./Listcoupons.css";

interface ListcouponsState {
	arryCouponDetails:CouponDetails[]
}

class Listcoupons extends Component<{}, ListcouponsState> {
    private myUrl = "http://localhost:8080/user/coupons/all";

    public constructor(props: {}) {
        super(props);
        this.state = {
			arryCouponDetails:[]
        };
    }

    private getData= async()=>{
        const response = await axios.get(this.myUrl);
        console.log(response.data);
        this.setState({
            arryCouponDetails:response.data
        })
    }

    public componentDidMount():void{
        this.getData();
    }

    public render(): JSX.Element {
        return (
            <div className="Listcoupons">
                {this.state.arryCouponDetails.map(item=><SingleCoupon image={item.image} title={item.title} price={item.price} amount={item.amount} endDate={item.endDate}/>)}
				
            </div>
        );
    }
}

export default Listcoupons;
