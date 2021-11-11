import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import SingleCustomer from "../../mainComponent/singleCustomer/singleCustomer";
import CustomerDetails from "../../model/CustomerDetails";
import { customerDownloadedAction } from "../../redux/CustomerState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import notify from "../../utils/Notify";
import "./searchCustomer.css";

interface searchCustomer{
    arrayCustomerDetails:CustomerDetails[]
}
function SearchCustomer(): JSX.Element {
    const myUrl = globals.urls.administrator + "customers/all";
    const{customerNum} = useParams<{customerNum?:string}>();
    var [customerDetails,setData] = useState(new CustomerDetails());
    let arrayCustomerDetails:CustomerDetails[]=[];

    useEffect(()=>{
        console.log(store.getState().customerState);
        if(store.getState().customerState.customers.length===0){
            jwtAxios.post(myUrl).then((response)=>{
                arrayCustomerDetails = response.data 
                //setData(response.data);
                store.dispatch(customerDownloadedAction(response.data))});
        }else{
            arrayCustomerDetails = store.getState().customerState.customers;
        }

        console.log(arrayCustomerDetails);
        var arrayOneCustomer = arrayCustomerDetails.filter(function(item){
            return item.id == Number(customerNum);
        });

        if(arrayOneCustomer.length<1){
            notify.error("customer was not found !!!");
            return
        }

        console.log(arrayOneCustomer[0]);
        setData(arrayOneCustomer[0])

        /*
        console.log(store.getState().authState.loginUser.clientType)
        
        if (store.getState().authState.loginUser.clientType != "administrator"){
           notify.error("You are not allowed to enter this page!!")
           history.push("/openPage");
            
        }
        */
        
    });
    return (
        <div className="searchCustomer">
            <SingleCustomer
                key={customerDetails.email}
                id={customerDetails.id}
                firstName={customerDetails.firstName}
                lastName={customerDetails.lastName}
                email={customerDetails.email}
                password={customerDetails.password}
                />
			
        </div>
    );
}

export default SearchCustomer;


