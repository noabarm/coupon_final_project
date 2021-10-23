import { Button, TextField } from "@mui/material";
import axios from "axios";
import jwtDecode from "jwt-decode";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useHistory } from "react-router-dom";
import AuthenToken from "../../model/AuthenToken";
import UserDetails from "../../model/UserDetails";
import { loginUserString, logoutUser } from "../../redux/AuthState";
import { companyCouponUpdateAction } from "../../redux/CompanyState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import notify from "../../utils/Notify";
import "./loginCompany.css";

function LoginCompany(): JSX.Element {
    const {register, handleSubmit, formState: { errors }} = useForm<UserDetails>();
    const [jwtToken,setToken] = useState("User has no token, bad bad user !!!");
    const history = useHistory();
    const myUrl = globals.urls.company + "login";
    var decodedState: AuthenToken;

    function send(userDetails:UserDetails){
        store.dispatch(logoutUser());
        store.dispatch(companyCouponUpdateAction());
        userDetails.clientType = "company";
        console.log("data arraived")
        console.log(userDetails.clientType);
        axios.post<string>(myUrl,userDetails)
        .then((response)=>{
            console.log(response.data);
            store.dispatch(loginUserString(response.data))
            console.log(store.getState().authState.loginUser.clientType)
            setToken(response.data);
            history.push("/company");
            notify.success("you login successfully!")
            
        })
        .catch(error=>{
            notify.error("you don't have permisson!!!")
            setToken("Error in getting response from the server");
        })
    }

    useEffect(()=>{
        if(store.getState().authState.loginUser.token !==""){
            decodedState =jwtDecode(store.getState().authState.loginUser.token);
            if ( decodedState.clientType === "company"){
                history.push("/company");  
            }
        }
    });
   
    
    return (
        <div className="loginCompany">
			<br/>
            <form onSubmit={handleSubmit(send)}>
            <div className="HeadLine">Company Login</div>
            <br/><br/>
			<TextField  label="email" variant="outlined" 
            {...register("email",{
                required :{value: true, message : "field is required"},
            })}/>
             <span> {errors.email && <p>{errors.email.message}</p>}</span>
            <br/><br/>
            <TextField  label="password" type="password" variant="outlined" 
            {...register("password",{
                required :{value: true, message : "field is required"},
            })}/>
            <span> {errors.password && <p>{errors.password.message}</p>}</span>
            <br/><br/><br/>
            <Button id="sendLoginButton" className="Button" type="submit" >Send</Button>
            </form>
            {/*<div>
                jwt token: <br/>
                {jwtToken}
            </div>
            */}
            <br/><br/>
        </div>
    );
}

export default LoginCompany;
