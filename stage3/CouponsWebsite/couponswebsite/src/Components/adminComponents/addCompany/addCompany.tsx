import { Button, TextField } from "@mui/material";
import { useForm } from "react-hook-form";
import CompanyDetails from "../../model/CompanyDetails";
import { companyAddAction } from "../../redux/CompanyState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import "./addCompany.css";
import notify from "../../utils/Notify";

function AddCompany(): JSX.Element {
    const {register, handleSubmit, formState: { errors }} = useForm<CompanyDetails>();
    const myUrl = globals.urls.administrator + "addCompany";
   
        
    function send(companyDetails:CompanyDetails){
        console.log(companyDetails);
        
        jwtAxios.post(myUrl,companyDetails).then((response)=>{
            console.log(response.config.headers.authorization);
            store.dispatch(companyAddAction(companyDetails));
            notify.success("company was add successfully!");
        }).catch(error=>{
            console.log(error);
            notify.error(error.response.data.description)});
    }

    return (
        <div className="addCompany">
			<h3>Add new company</h3>
                <form onSubmit={handleSubmit(send)}>
                    <TextField label="Company name" variant="outlined"
                        {...register("name", {
                            required: {value: true, message: "this field is required"},
                            minLength: {value: 2,  message: "min length is 2 letters"},
                            maxLength: {value: 40, message:"max length is 40 letters"},
                    })}/>
                     <span> {errors.name && <p>{errors.name.message}</p>}</span>
                    <br/><br/>

                     <TextField label="Company email" variant="outlined"
                        {...register("email", {
                            required: {value: true, message: "this field is required"},
                            minLength: {value: 10,  message: "min length is 10 letters"},
                            maxLength: {value: 40, message:"max length is 40 letters"},
                    })}/>
                     <span> {errors.email && <p>{errors.email.message}</p>}</span>
                    <br/><br/>

                     <TextField label="Company password" variant="outlined"
                        {...register("password", {
                            required: {value: true, message: "this field is required"},
                            minLength: {value: 4,  message: "min length is 4 letters"},
                            maxLength: {value: 8, message:"max length is 8 letters"},
                    })}/>
                     <span> {errors.password && <p>{errors.password.message}</p>}</span>

                    <br/><br/>
                  
                    <Button id ="sendButton" type="submit" >Send</Button>
                   

                </form>
        </div>
    );
}

export default AddCompany;
