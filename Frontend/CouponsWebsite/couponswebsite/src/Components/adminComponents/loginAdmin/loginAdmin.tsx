import { Button, TextField } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useHistory } from "react-router-dom";
import UserDetails from "../../model/UserDetails";
import { loginUserString } from "../../redux/AuthState";
import store from "../../redux/store";
import "./loginAdmin.css";
import globals from "../../utils/Globals";
import notify from "../../utils/Notify";
import jwtDecode from "jwt-decode";
import AuthenToken from "../../model/AuthenToken";

function LoginAdmin(): JSX.Element {
    const {register, handleSubmit, formState: { errors }} = useForm<UserDetails>();
    const [jwtToken,setToken] = useState("User has no token, bad bad user !!!");
    const history = useHistory();
    const myUrl = globals.urls.administrator + "login";
    let decodedState: AuthenToken;

    function send(userDetails:UserDetails){
        userDetails.clientType = "administrator";
        console.log("data arraived")
        console.log(userDetails.clientType);
        axios.post<string>(myUrl,userDetails)
        .then((response)=>{
            console.log(response.data);
            store.dispatch(loginUserString(response.data));
            console.log(store.getState().authState.loginUser.clientType);
            setToken(response.data);
            history.push("/administrator");
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
            if ( decodedState.clientType === "administrator"){
                history.push("/administrator");  
            }
        }
    });


    
    return (
        <div className="loginAdmin">
            <br/>
            <form onSubmit={handleSubmit(send)}>
            <div className="HeadLine">Admin Login</div>
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

export default LoginAdmin; 
