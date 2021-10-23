import { Route, Switch } from "react-router-dom";
import CompanyDetailsComp from "../CompanyDetailsComp/CompanyDetailsComp";
import CompanyOpenPage from "../companyOpenPage/companyOpenPage";
import CouponsListCamp from "../couponsListCamp/couponsListCamp";
import "./companyRouting.css";

function CompanyRouting(): JSX.Element {
    return (
        <div className="companyRouting">
            <Switch>
                <Route path="/company" component={CompanyOpenPage} exact/>
                <Route path="/allCoupons" component={CouponsListCamp} exact/>
                <Route path="/companyDetails" component={CompanyDetailsComp} exact/>
            </Switch>
			
        </div>
    );
}

export default CompanyRouting;
