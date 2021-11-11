import axios from "axios";
import { loginUser, loginUserString } from "../redux/AuthState";
import store from "../redux/store";


//יצירת אובייקט מוכן של
//axios => singleTon
const jwtAxios = axios.create();

//Request Interceptor - מה אנחנו רוצים לבצע מראש בעת שליחת בקשה לשרת
/*
jwtAxios.interceptors.request.use(request=>{
    request.headers = {
        "Authorization" : store.getState().authState.loginUser.token,
    }
    return request;
});


jwtAxios.interceptors.response.use(response =>{
    store.dispatch(loginUser(response.headers.Authorization));
    return response;
})
*/
jwtAxios.interceptors.request.use(request=>{
    request.headers.authorization = 
         store.getState().authState.loginUser.token;
    
    return request;
})

jwtAxios.interceptors.response.use(response =>{
    store.dispatch(loginUserString(response.headers.authorization));
    console.log(response);
    return response;
},)


export default jwtAxios;
