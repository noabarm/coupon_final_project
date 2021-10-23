import { Component } from "react";
import "./SingleCoupon.css";

interface SingleCouponProps {
    title:string,
    endDate:string,
    amount:number,
    price:number,
    image:string
}

class SingleCoupon extends Component<SingleCouponProps> {
private imgkey= "https://backendlessappcontent.com/AE176C35-B9A0-07F6-FF0E-242CF8C99600/ACFDB970-70A6-45A1-9877-9716DEE3861D/files/img/" + this.props.image;
    

    public render(): JSX.Element {
        
        return (
            <div className="SingleCoupon couponBox">
                <img src={this.imgkey} width="200px" height="150px"/>
				<hr/>
                {this.props.title}<br/><br/>
                price: {this.props.price}$<br/>
                only {this.props.amount} left!<br/>
                end date: {this.props.endDate}
            </div>
        );
    }
}

export default SingleCoupon;
