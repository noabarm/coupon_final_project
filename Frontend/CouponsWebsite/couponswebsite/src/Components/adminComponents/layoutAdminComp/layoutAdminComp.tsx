import jwtDecode from "jwt-decode";
import { useEffect } from "react";
import { BrowserRouter, useHistory } from "react-router-dom";
import AuthenToken from "../../model/AuthenToken";
import { loginUserString } from "../../redux/AuthState";
import store from "../../redux/store";
import notify from "../../utils/Notify";
import AdminAside from "../adminAside/adminAside";
import AdminRouting from "../adminRouting/adminRouting";
import "./layoutAdminComp.css";

function LayoutAdminComp(): JSX.Element {
    let decoded: AuthenToken;
    const history = useHistory();
    
    useEffect(()=>{
        decoded = jwtDecode(localStorage.getItem("token"));

        console.log(decoded.clientType);

        if (decoded.clientType !== "administrator"){
           notify.error("You are not allowed to enter this page!!")
           history.push("/openPage");    
        }

        store.dispatch(loginUserString(localStorage.getItem("token")));
        console.log(store.getState().authState.loginUser); 
    });

    return (
        <div className="layoutAdminComp">
            <BrowserRouter>
                <header className="adminHeader">
                    <AdminAside/>
                </header>
                <main className="adminMain">
                    <AdminRouting/>
                </main>
            </BrowserRouter>
			
        </div>
    );
}

export default LayoutAdminComp;
