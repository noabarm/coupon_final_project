
import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import CouponDetails from "../../model/CouponDetails";
import { couponDownloadedAction } from "../../redux/CouponState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import SingleCouponRow from "../singleCouponRow/singleCouponRow";
import "./couponCompanyList.css";

interface CouponCompanyListProps{
    companyId:number,
    arrayCouponDetails:CouponDetails[]
}

function CouponCompanyList(props: CouponCompanyListProps ): JSX.Element {
    const myUrl =globals.urls.guest + "coupons/all";

    const{companyNum} = useParams<{companyNum?:string}>();

    const [arrayCouponDetails,setData] = useState([]);

    var arrayByCompany = arrayCouponDetails.filter(function(item){
        return item.companyID == companyNum;
    });
    
    useEffect(()=>{
        console.log("coupns list strat");
        if (store.getState().couponState.coupons.length===0){
            axios.get(myUrl).then((response)=>{
                setData(response.data);
                store.dispatch(couponDownloadedAction(response.data))})
                .catch(error=>{console.log(error)});
        } else {
            setData(store.getState().couponState.coupons);
        }

        
       
        console.log(companyNum)
        console.log(arrayByCompany)
    });
    return (
        <div className="couponCompanyList">
            <header>
			<h1>list of coupons:</h1>
            </header>
            <main>
            {arrayByCompany.map(item=><SingleCouponRow 
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
            <br/><br/>
        </div>
    );
}

export default CouponCompanyList;
