
import { combineReducers, createStore } from "redux";
import { authReducer } from "./AuthState";
import { companyReducer } from "./CompanyState";
import { couponReducer } from "./CouponState";
import { customerReducer } from "./CustomerState";


const reducers = combineReducers({
    couponState: couponReducer,
    authState: authReducer,
    companyState: companyReducer,
    customerState: customerReducer,
})

const store = createStore(reducers);


export default store;

