import jwtDecode from "jwt-decode";
import { useEffect } from "react";
import { BrowserRouter, useHistory } from "react-router-dom";
import AuthenToken from "../../model/AuthenToken";
import { loginUserString } from "../../redux/AuthState";
import store from "../../redux/store";
import notify from "../../utils/Notify";
import CompanyAside from "../companyAside/companyAside";
import CompanyRouting from "../companyRouting/companyRouting";
import "./layoutCompanyComp.css";

function LayoutCompanyComp(): JSX.Element {
    let decoded: AuthenToken;
    const history = useHistory();
    
    useEffect(()=>{
        decoded = jwtDecode(localStorage.getItem("token"));

        console.log(decoded.clientType);

        if (decoded.clientType !== "company"){
           notify.error("You are not allowed to enter this page!!")
           history.push("/openPage");    
        }

        store.dispatch(loginUserString(localStorage.getItem("token")));
        console.log(store.getState().authState.loginUser); 
    });
    
    return (
        <div className="layoutCompanyComp">
			<BrowserRouter>
                <header className="companyHeader">
                    <CompanyAside/>
                </header>
                <main className="companyMain">
                    <CompanyRouting/>
                </main>
            </BrowserRouter>
        </div>
    );
}

export default LayoutCompanyComp;
