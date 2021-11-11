import { useEffect, useState } from "react";
import CompanyDetails from "../../model/CompanyDetails";
import { companyDownloadedAction } from "../../redux/CompanyState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import "./companyOpenPage.css";

function CompanyOpenPage(): JSX.Element {
    const myUrl = globals.urls.company + "details";
    let oneCompanyArray:CompanyDetails[] = [];
    const [companyName,setName] = useState<string>("");

    useEffect(()=>{
        console.log(store.getState().companyState.companies);
        jwtAxios.post(myUrl).then((response)=>{
            console.log(response.data);
            //let oneCompanyArray:CompanyDetails[] = []; 
            oneCompanyArray.push(response.data);
            setName(oneCompanyArray[0].name);
            store.dispatch(companyDownloadedAction(oneCompanyArray));
        }
        ).catch(error=>{console.log(error)});
    })  

    return (
        <div className="companyOpenPage">
			 <div className="discriptionCompany">
                <div className="buneerTitle">
                HELLO {companyName} <br/>
                </div>
                <div className="aboutUsCompany">
                    You are in your personal page.<br/>
                    Here you can see all the coupons you have that in system.<br/>
                    To see all coupons details press on coupons tab,<br/>
                    also you can see your company details.<br/>
                    To see all details press on my details tab,<br/>
                    also you can add/update and delete any coupon you like.<br/>
                </div>
            </div>
        </div>
    );
}

export default CompanyOpenPage;
