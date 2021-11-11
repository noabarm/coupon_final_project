import CustomerDetails from "../model/CustomerDetails";

export class CustomerState{
    public customers: CustomerDetails[] = [];
}

export enum CustomerActionType{
    CustomerDownloaded = "CustomerDownloaded",
    CustomerAdd = "CustomerAdd",
    CustomerUpdate = "CustomerUpdate",
    CustomerDelete = "CustomerDelete",
    CustomerCouponUpdate = "CustomerCouponUpdate"
}

export interface CustomerAction{
    type: CustomerActionType,
    payload?: any,
}

//action functions
export function customerDownloadedAction(customers: CustomerDetails[]):CustomerAction{
    return{type: CustomerActionType.CustomerDownloaded, payload: customers}
}

export function customerAddAction (customer: CustomerDetails):CustomerAction{
    return{type:CustomerActionType.CustomerAdd, payload: customer}
}

export function customerUpdateAction(customer: CustomerDetails):CustomerAction{
    return{ type: CustomerActionType.CustomerUpdate, payload: customer}
}

export function customerDeleteAction(id: number):CustomerAction{
    return{ type:CustomerActionType.CustomerCouponUpdate, payload: id}
}

export function customerCouponUpdateAction():CustomerAction{
    return{ type: CustomerActionType.CustomerCouponUpdate}
}

//reducer
export function customerReducer(currentState: CustomerState = new CustomerState(), action: CustomerAction):CustomerState{
    const newState = {...currentState};

    switch (action.type){
        case CustomerActionType.CustomerDownloaded:
            newState.customers = action.payload;
            break;

        case CustomerActionType.CustomerAdd:
            newState.customers = [];
            break;

        case CustomerActionType.CustomerUpdate:
            newState.customers = [];
            break;
        
        case CustomerActionType.CustomerDelete:
            newState.customers = [];
            break;
        case CustomerActionType.CustomerCouponUpdate:
            newState.customers = [];
            break;
    }

    return newState;

}