import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import CouponDetails from "../../model/CouponDetails";
import CustomerDetails from "../../model/CustomerDetails";
import { customerDownloadedAction } from "../../redux/CustomerState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import SingleCouponRow from "../singleCouponRow/singleCouponRow";
import "./couponCustomerList.css";

interface CouponCustomerListProps{
    arrayCouponDetails:CouponDetails[],
    customersDetails:CustomerDetails[],
}

function CouponCustomerList(props: CouponCustomerListProps ): JSX.Element {
    
    const myUrl = globals.urls.administrator + "customers/all";


    const{customerNum} = useParams<{customerNum?:string}>();
    var [customersDetails,setData] = useState([]);
    var [arrayCouponDetails,setCoupns]=useState([]);
    

    useEffect(()=>{
        console.log("customer list strat");
        console.log("customer number is:" +customerNum);
            if (store.getState().customerState.customers.length===0){
                jwtAxios.post(myUrl).then((response)=>{
                    console.log(response.data)
                    setData(response.data);
                    store.dispatch(customerDownloadedAction(response.data))})
                    .catch(error=>{console.log(error)});
            } else {
                setData(store.getState().customerState.customers);
            }
            if(customersDetails.length>0){
            console.log(customersDetails);
            var arrayByCustomer = customersDetails.filter(function(item){
                return item.id == Number(customerNum);
            });
            
            setCoupns(arrayByCustomer[0].coupons);
           
            console.log("only coupons");
            console.log(arrayCouponDetails);
            }
            
        });

    return (
        <div className="couponCustomerList">
            <header>
            <h1>list of coupons:</h1>
            </header>
            <main>
            {arrayCouponDetails.map(item=><SingleCouponRow 
             key={item.id}
             id={item.id}
             category={item.category}
             title={item.title} 
             endDate={item.endDate}
             startDate={item.startDate}
             amount={item.amount} 
             price={item.price} 
             image={item.image}
             description={item.description}
             companyID={item.companyID}              
            />)}
            </main>
            <br/><br/><br/><br/><br/> 
        </div>
    );
}

export default CouponCustomerList;
