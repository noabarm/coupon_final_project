import OnlineUser from "../model/OnlineUser";
import jwt_decode from "jwt-decode";

export class AuthState{
    public loginUser:OnlineUser = new OnlineUser();
}

export enum AuthActionType{
    LoginUser = "loginUser",
    LogoutUser = "LogoutUser",
    LoginUserString = "LoginUserString",
}

export interface AuthAction{
    type:AuthActionType,
    payload?:any,
}

export function loginUser(user: OnlineUser):AuthAction{
    return {type: AuthActionType.LoginUser, payload: user}
}

export function logoutUser():AuthAction{
    return {type: AuthActionType.LogoutUser, payload: null}
}

export function loginUserString(token:string):AuthAction{
    return{type:AuthActionType.LoginUserString, payload:token}
}

export function authReducer(currentState: AuthState = new AuthState(), action:AuthAction):AuthState{
    const newState = {...currentState};

    switch(action.type){
        case AuthActionType.LoginUser:
            newState.loginUser = action.payload;
            //insert token with value of the token into local storage
            localStorage.setItem("token",action.payload)
        break;

        case AuthActionType.LogoutUser:
            newState.loginUser = new OnlineUser();
            //remove the token from local storage
            localStorage.removeItem("token");
        break;

            //get the string, extract the client type, and update the authState
            //save the data to local storage
        case AuthActionType.LoginUserString:
            console.log(action);
            newState.loginUser.token = action.payload;
            localStorage.setItem("token",newState.loginUser.token);
        break;

    }
    return newState;
}