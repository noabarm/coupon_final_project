import { Route, Switch } from "react-router-dom";
import CouponsListCustomer from "../couponsListCustomer/couponsListCustomer";
import CustomerDetailsComp from "../customerDetailsComp/customerDetailsComp";
import CustomerOpenPage from "../customerOpenPage/customerOpenPage";
import "./customerRouting.css";

function CustomerRouting(): JSX.Element {
    return (
        <div className="customerRouting">
			<Switch>
                <Route path="/customer" component={CustomerOpenPage} exact/>
                <Route path="/allCustomerCoupons" component={CouponsListCustomer} exact/>
                <Route path="/customerDetails" component={CustomerDetailsComp} exact/>
            </Switch>
        </div>
    );
}

export default CustomerRouting;
