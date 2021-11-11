
import "./adminRouting.css";
import { Route, Switch } from "react-router-dom";
import AdminOpenPage from "../adminOpenPage/adminOpenPage";
import AddCompany from "../addCompany/addCompany";
import CompanysList from "../companysList/companysList";
import CouponCompanyList from "../couponCompanyList/couponCompanyList";
import SearchCompany from "../searchCompany/searchCompany";
import AddCustomer from "../addCustomer/addCustomer";
import CustomersList from "../customersList/customersList";
import SearchCustomer from "../searchCustomer/searchCustomer";
import CouponCustomerList from "../couponCustomerList/couponCustomerList";
function AdminRouting(): JSX.Element {
    return (
        <div className="adminRouting">
			<Switch>
                <Route path="/administrator" component={AdminOpenPage} exact/>
                <Route path="/addCompany" component={AddCompany} exact/>
                <Route path="/allCompanies" component={CompanysList} exact/>
                <Route path="/companyCoupnsList/:companyNum" component={CouponCompanyList} exact/>
                <Route path="/searchCompany/:companyNum" component={SearchCompany} exact/>
                <Route path="/addCustomer" component={AddCustomer} exact/>
                <Route path="/allCustomers" component={CustomersList} exact/>
                <Route path="/customerCoupnsList/:customerNum" component={CouponCustomerList} exact/>
                <Route path="/searchCustomer/:customerNum" component={SearchCustomer} exact/>
            </Switch>
        </div>
    );
}

export default AdminRouting;
