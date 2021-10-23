import { Redirect, Route, Switch } from "react-router-dom";
import LoginAdmin from "../../adminComponents/loginAdmin/loginAdmin";
import LoginCompany from "../../companyComponents/loginCompany/loginCompany";
import LoginCustomer from "../../customerComponents/loginCustomer/loginCustomer";
import MainScreen from "../mainScreen/mainScreen";
import Page404 from "../Page404/Page404";
import LayoutAdminComp from "../../adminComponents/layoutAdminComp/layoutAdminComp";
import LayoutCompanyComp from "../../companyComponents/layoutCompanyComp/layoutCompanyComp";
import LayoutCustomer from "../../customerComponents/layoutCustomer/layoutCustomer";


function Routing(): JSX.Element {
    return (
        <div className="Routing">
			<Switch>
                <Route path="/openPage" component={MainScreen} exact/>
                <Route path="/loginAdmin" component={LoginAdmin} exact/>
                <Route path="/loginCompany" component={LoginCompany} exact/>
                <Route path="/loginCustomer" component={LoginCustomer} exact/>
                <Route path="/administrator" component={LayoutAdminComp} exact/>
                <Route path="/company" component={LayoutCompanyComp} exact/>
                <Route path="/customer" component={LayoutCustomer} exact/>
                <Redirect from="/" to="/openPage" exact/>
                <Route component={Page404}/>

            </Switch>
        </div>
    );
}

export default Routing;
