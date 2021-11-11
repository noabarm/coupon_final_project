import { SyntheticEvent } from "react";
import { companyDeleteAction } from "../../redux/CompanyState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import notify from "../../utils/Notify";
import "./deleteCompany.css";

function DeleteCompany(): JSX.Element {
    
    const myUrl = globals.urls.administrator + "deleteCompany/";
    let companyNumber:number=0;

    function updateNumber(args:SyntheticEvent){
        companyNumber = Number((args.target as HTMLInputElement).value);
        console.log(companyNumber);
    }

    function deleteCompany(){
        jwtAxios.delete(myUrl+companyNumber).then((response)=>{
            console.log(response.config.headers.authorization);
            store.dispatch(companyDeleteAction(companyNumber));
            notify.success("company was deleted successfully!");
    
        })
    }

    return (
        <div className="deleteCompany">
           <h3>Delete Company</h3><br/>
                <input type="number" placeholder="please enter company id number" onChange={updateNumber}/>
                <input type="button" value="Delete" onClick={deleteCompany}/><hr/>
			
        </div>
    );
}

export default DeleteCompany;
