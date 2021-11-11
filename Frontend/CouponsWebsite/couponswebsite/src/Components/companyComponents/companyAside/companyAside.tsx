import { NavLink } from "react-router-dom";
import "./companyAside.css";

function CompanyAside(): JSX.Element {
    return (
        <div className="companyAside">
			<h1 className="title">Company
            </h1><br/>
            <nav>
                <NavLink className="companyNav" exact to="/companyDetails">My Details</NavLink>
                |
                <NavLink className="companyNav" exact to="/allCoupons">Coupons</NavLink>
               
            </nav>
        </div>
    );
}

export default CompanyAside;
