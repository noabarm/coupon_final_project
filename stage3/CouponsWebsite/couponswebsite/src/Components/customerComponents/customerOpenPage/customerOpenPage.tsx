import { useEffect, useState } from "react";
import CustomerDetails from "../../model/CustomerDetails";
import { customerDownloadedAction } from "../../redux/CustomerState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import "./customerOpenPage.css";

function CustomerOpenPage(): JSX.Element {

    const myUrl = globals.urls.customer + "details";
    let oneCustomerArray:CustomerDetails[] = [];
    const [customerName,setName] = useState<string>("");

    useEffect(()=>{
        console.log(store.getState().customerState.customers);
        jwtAxios.post(myUrl).then((response)=>{
            console.log(response.data);
            //let oneCompanyArray:CompanyDetails[] = []; 
            oneCustomerArray.push(response.data);
            setName(oneCustomerArray[0].firstName);
            store.dispatch(customerDownloadedAction(oneCustomerArray));
        }
        ).catch(error=>{console.log(error)});
    })

    return (
        <div className="customerOpenPage">
			<div className="discriptionCustomer">
                <div className="buneerTitle">
                HELLO {customerName} <br/>
                </div>
                <div className="aboutUsCustomer">
                    You are in your personal page.<br/>
                    Here you can see all the coupons you purchased.<br/>
                    To see all coupons details press on coupons tab.<br/>
                    Also you can see your customer details.<br/>
                    To see all details press on my details tab.<br/>
                </div>
            </div>
        </div>
    );
}

export default CustomerOpenPage;
