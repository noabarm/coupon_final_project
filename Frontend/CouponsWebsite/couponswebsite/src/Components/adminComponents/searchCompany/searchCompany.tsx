import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import SingleCompany from "../../mainComponent/singleCompany/singleCompany";
import CompanyDetails from "../../model/CompanyDetails";
import { companyDownloadedAction } from "../../redux/CompanyState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import notify from "../../utils/Notify";
import "./searchCompany.css";

interface searchCompany{
    arrayCompanyDetails:CompanyDetails[]
}

function SearchCompany(): JSX.Element {
    const myUrl = globals.urls.administrator + "companies/all";
    const{companyNum} = useParams<{companyNum?:string}>();
    let [companyDetails,setData] = useState(new CompanyDetails());
    let arrayCompanyDetails:CompanyDetails[]=[];

   /*
    function searchCompany(){
        console.log(store.getState().companyState);
        if(store.getState().companyState.companies.length===0){
            jwtAxios.post(myUrl).then((response)=>{
                arrayCompanyDetails = response.data 
                //setData(response.data);
                store.dispatch(companyDownloadedAction(response.data))});
        }else{
            arrayCompanyDetails = store.getState().companyState.companies;
        }

        console.log(arrayCompanyDetails);
        var arrayOneCompany = arrayCompanyDetails.filter(function(item){
            return item.id == Number(companyNum);
        });

        if(arrayOneCompany.length<1){
            notify.error("company was not found !!!");
            return
        }

        console.log(arrayOneCompany[0]);
        setData(arrayOneCompany[0])
        notify.success("company was found in the system !!!");

    }
    */
    
    useEffect(()=>{
        console.log(store.getState().companyState);
        if(store.getState().companyState.companies.length===0){
            jwtAxios.post(myUrl).then((response)=>{
                arrayCompanyDetails = response.data 
                //setData(response.data);
                store.dispatch(companyDownloadedAction(response.data))});
        }else{
            arrayCompanyDetails = store.getState().companyState.companies;
        }

        console.log(arrayCompanyDetails);
        let arrayOneCompany = arrayCompanyDetails.filter(function(item){
            return item.id == Number(companyNum);
        });

        if(arrayOneCompany.length<1){
            notify.error("company was not found !!!");
            return
        }

        console.log(arrayOneCompany[0]);
        setData(arrayOneCompany[0])

        /*
        console.log(store.getState().authState.loginUser.clientType)
        
        if (store.getState().authState.loginUser.clientType != "administrator"){
           notify.error("You are not allowed to enter this page!!")
           history.push("/openPage");
            
        }
        */
        
    });

    return (
        <div className="searchCompany">
            {/*
			<h3>Search Company</h3><br/>
            
            <input type="number" placeholder="please enter company id number" onChange={updateNumber}/>
            <input type="button" value="Search" onClick={searchCompany}/><hr/>
            */}
            <SingleCompany
                key={companyDetails.email}
                id={companyDetails.id}
                name={companyDetails.name}
                email={companyDetails.email}
                password={companyDetails.password}
                />
        </div>
    );
}

export default SearchCompany;
