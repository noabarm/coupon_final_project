import { NavLink } from "react-router-dom";
import "./adminAside.css";

function AdminAside(): JSX.Element {
    return (
        <div className="adminAside">
            <h1 className="title">Admin</h1><br/>
            <nav>
                <NavLink className="adminNav" exact to="/allCompanies">Companies</NavLink>
                |
                <NavLink className="adminNav" exact to="/allCustomers">Customers</NavLink>
               
            </nav>
        </div>
    );
}

export default AdminAside;
