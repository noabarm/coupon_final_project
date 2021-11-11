import { NavLink } from "react-router-dom";
import "./customerAside.css";

function CustomerAside(): JSX.Element {
    return (
        <div className="customerAside">
            <h1 className="title">Customer
            </h1><br/>
            <nav>
                <NavLink className="customerNav" exact to="/customerDetails">My Details</NavLink>
                |
                <NavLink className="customerNav" exact to="/allCustomerCoupons">Coupons</NavLink>
               
            </nav>
			
        </div>
    );
}

export default CustomerAside;
