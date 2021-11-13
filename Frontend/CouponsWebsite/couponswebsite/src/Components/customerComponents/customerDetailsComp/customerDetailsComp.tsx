import jwtDecode from "jwt-decode";
import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import AuthenToken from "../../model/AuthenToken";
import CustomerDetails from "../../model/CustomerDetails";
import { loginUserString } from "../../redux/AuthState";
import { customerDownloadedAction } from "../../redux/CustomerState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import notify from "../../utils/Notify";
import "./customerDetailsComp.css";

interface CustomerDetailsComp{
    customerDetails:CustomerDetails[];
}

function CustomerDetailsComp(): JSX.Element {
    let decoded: AuthenToken;
    const history = useHistory();
    const myUrl = globals.urls.customer + "details";
    const [customerDetails,setData] = useState([new CustomerDetails()]);
    const goCoupons =()=>{
        history.push("/allCustomerCoupons")
    }

    useEffect(()=>{
       
        decoded = jwtDecode(localStorage.getItem("token"));

        console.log(decoded.clientType);

        if (decoded.clientType !== "customer"){
           notify.error("You are not allowed to enter this page!!")
           history.push("/openPage");    
        }

        store.dispatch(loginUserString(localStorage.getItem("token")));

        console.log(store.getState().customerState.customers);

        if(store.getState().customerState.customers.length===0){
            jwtAxios.post(myUrl)
                .then((response)=>{
                console.log(response.data);
                let oneCustomerArray:CustomerDetails[] = []; 
                oneCustomerArray.push(response.data);
                store.dispatch(customerDownloadedAction(oneCustomerArray));
                setData(oneCustomerArray);
                console.log(customerDetails)
            }).catch(error=>{console.log(error)}); 
        }else{
            setData(store.getState().customerState.customers);
            console.log(customerDetails)
        }                        
    });
    return (
        <div className="customerDetailsComp">
            <main>
                <div className="ColumDetails">
                Id number
                </div>
                <div className="ColumDetails">
			    first Name:
                </div>
                <div className="ColumDetails">
			    Last Name:
                </div>
                <div className="ColumDetails">
                Email:
                </div>
                <div className="ColumDetails">
                Password
                </div>
            </main>
            <div className="singleCustomerComp">
                <div className="Colum1">
                {customerDetails[0].id}
                </div>
                <div className="Colum1">
			    {customerDetails[0].firstName}
                </div>
                <div className="Colum1">
			    {customerDetails[0].lastName}
                </div>
                <div className="Colum1">
                {customerDetails[0].email}
                </div>
                <div className="Colum1">
                {customerDetails[0].password}
                </div>
               
                <input id="listButtonComp" type="button" value="Coupns List" onClick={goCoupons} />
                
            </div>
			
        </div>
    );
}

export default CustomerDetailsComp;
