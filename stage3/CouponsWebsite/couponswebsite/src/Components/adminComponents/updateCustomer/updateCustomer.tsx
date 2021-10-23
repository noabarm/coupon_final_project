import { Button, TextField } from "@mui/material";
import { useForm } from "react-hook-form";
import { useHistory } from "react-router-dom";
import CustomerDetails from "../../model/CustomerDetails";
import { customerUpdateAction } from "../../redux/CustomerState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import notify from "../../utils/Notify";
import "./updateCustomer.css";
interface updateCustomerPrpos{
    id:number,
}

function UpdateCustomer(props:updateCustomerPrpos): JSX.Element {
    const {register, handleSubmit, formState: { errors }} = useForm<CustomerDetails>();
    const myUrl = globals.urls.administrator + "updateCustomer";
    let history = useHistory();

    function send(customerDetails:CustomerDetails){
        customerDetails.id = props.id;
        console.log(customerDetails);
        jwtAxios.post(myUrl,customerDetails).then((response)=>{
            console.log(customerDetails);
            console.log(response.config.headers.authorization);
            store.dispatch(customerUpdateAction(customerDetails));
            notify.success("customer was update successfully!");
            history.push("/allCustomers");
        }).catch(error=>console.log(error));
    }
    return (
        <div className="updateCustomer">
            <h3>update customer</h3>
                <form onSubmit={handleSubmit(send)}>
                    <TextField label="Customer first name" variant="outlined"
                        {...register("firstName", {
                            required: {value: true, message: "this field is required"},
                            minLength: {value: 2,  message: "min length is 2 letters"},
                            maxLength: {value: 10, message:"min length is 10 letters"},
                    })}/>
                     <span> {errors.firstName && <p>{errors.firstName.message}</p>}</span>
                    <br/><br/>
                    <TextField label="Customer last name" variant="outlined"
                        {...register("lastName", {
                            required: {value: true, message: "this field is required"},
                            minLength: {value: 2,  message: "min length is 2 letters"},
                            maxLength: {value: 10, message:"min length is 10 letters"},
                    })}/>
                     <span> {errors.lastName && <p>{errors.lastName.message}</p>}</span>
                    <br/><br/>
                     <TextField label="Customer email" variant="outlined"
                        {...register("email", {
                            required: {value: true, message: "this field is required"},
                            minLength: {value: 10,  message: "min length is 10 letters"},
                            maxLength: {value: 40, message:"min length is 40 letters"},
                    })}/>
                     <span> {errors.email && <p>{errors.email.message}</p>}</span>
                    <br/><br/>

                     <TextField label="Customer password" variant="outlined"
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

export default UpdateCustomer;
