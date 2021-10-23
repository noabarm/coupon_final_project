import { Button, TextField } from "@mui/material";
import { useForm } from "react-hook-form";
import { useHistory } from "react-router-dom";
import CompanyDetails from "../../model/CompanyDetails";
import { companyUpdateAction } from "../../redux/CompanyState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import notify from "../../utils/Notify";
import "./updateCompany.css";
interface updateCompanyprpos{
    id:number,
    name:string,
}

function UpdateCompany(props:updateCompanyprpos ): JSX.Element {
    const {register, handleSubmit, formState: { errors }} = useForm<CompanyDetails>();
    const myUrl = globals.urls.administrator + "updateCompany";
    let history = useHistory();

    function send(companyDetails:CompanyDetails){
        companyDetails.id = props.id;
        companyDetails.name = props.name;
        console.log(companyDetails);
        jwtAxios.post(myUrl,companyDetails).then((response)=>{
            console.log(companyDetails);
            console.log(response.config.headers.authorization);
            store.dispatch(companyUpdateAction(companyDetails));
            notify.success("company was update successfully!");
            history.push("/allCompanies");
        }).catch(error=>{console.log(error)});
    }
    
    return (
        <div className="updateCompany">
            <h3>update company</h3>
                <form onSubmit={handleSubmit(send)}>
                    <TextField label="Company email" variant="outlined"
                        {...register("email", {
                            required: {value: true, message: "this field is required"},
                            minLength: {value: 10,  message: "min length is 10 letters"},
                            maxLength: {value: 40, message:"min length is 40 letters"},
                    })}/>
                    <span> {errors.email && <p>{errors.email.message}</p>}</span>
                    <br/><br/>

                    <TextField label="Company password" variant="outlined"
                        {...register("password", {
                            required: {value: true, message: "this field is required"},
                            minLength: {value: 4,  message: "min length is 4 letters"},
                            maxLength: {value: 8, message:"min length is 8 letters"},
                    })}/>
                    <span> {errors.password && <p>{errors.password.message}</p>}</span>

                    <br/><br/>
                   
                    <Button id ="sendButton" type="submit" >Send</Button>
                    

                </form>
			
        </div>
    );
}

export default UpdateCompany;
