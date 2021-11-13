import jwtDecode from "jwt-decode";
import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import AuthenToken from "../../model/AuthenToken";
import CompanyDetails from "../../model/CompanyDetails";
import { loginUserString } from "../../redux/AuthState";
import { companyDownloadedAction } from "../../redux/CompanyState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import notify from "../../utils/Notify";
import "./CompanyDetailsComp.css";

interface CompanyDetailsComp{
    companyDetails:CompanyDetails[];
}

function CompanyDetailsComp(): JSX.Element {
    let decoded: AuthenToken;
    const history = useHistory();
    const myUrl = globals.urls.company + "details";
    const [companyDetails,setData] = useState([new CompanyDetails()]);
    const goCoupons =()=>{
        history.push("/allCoupons")
    }
    
    useEffect(()=>{
       
        decoded = jwtDecode(localStorage.getItem("token"));

        console.log(decoded.clientType);

        if (decoded.clientType !== "company"){
           notify.error("You are not allowed to enter this page!!")
           history.push("/openPage");    
        }

        store.dispatch(loginUserString(localStorage.getItem("token")));

        console.log(store.getState().companyState.companies);

        if(store.getState().companyState.companies.length===0){
            jwtAxios.post(myUrl)
                .then((response)=>{
                console.log(response.data);
                let oneCompanyArray:CompanyDetails[] = []; 
                oneCompanyArray.push(response.data);
                store.dispatch(companyDownloadedAction(oneCompanyArray));
                setData(oneCompanyArray);
                console.log(companyDetails)
            }).catch(error=>{console.log(error)}); 
        }else{
            setData(store.getState().companyState.companies);
            console.log(companyDetails)
        }                        
    });
    return (
        <div className="CompanyDetailsComp">
            <main>
                <div className="ColumDetails">
                Id number
                </div>
                <div className="ColumDetails">
			    Name:
                </div>
                <div className="ColumDetails">
                Email:
                </div>
                <div className="ColumDetails">
                Password
                </div>
            </main>
            <div className="singleCompanyComp">
                <div className="Colum1">
                {companyDetails[0].id}
                </div>
                <div className="Colum1">
			    {companyDetails[0].name}
                </div>
                <div className="Colum1">
                {companyDetails[0].email}
                </div>
                <div className="Colum1">
                {companyDetails[0].password}
                </div>
               
                <input id="listButtonComp" type="button" value="Coupns List" onClick={goCoupons} />
                
            </div>
        </div>
    );
}

export default CompanyDetailsComp;
