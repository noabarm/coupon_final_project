import CompanyDetails from "../model/CompanyDetails";
import CouponDetails from "../model/CouponDetails";

export class CompanyState{
    public companies: CompanyDetails[] = [];
}

export enum CompanyActionType{
    CompanyDownloaded = "CompanyDownloaded",
    CompanyAdd = "CompanyAdd",
    CompanyUpdate = "CompanyUpdate",
    CompanyDelete = "CompanyDelete",
    CompanyCouponUpdate = "CompanyCouponUpdate"
}

export interface CompanyAction{
    type: CompanyActionType,
    payload?: any,
}

//action functions
export function companyDownloadedAction(companies: CompanyDetails[]):CompanyAction{
    return{type: CompanyActionType.CompanyDownloaded, payload: companies}
}

export function companyAddAction (company: CompanyDetails):CompanyAction{
    return{type:CompanyActionType.CompanyAdd, payload: company}
}

export function companyUpdateAction(company: CompanyDetails):CompanyAction{
    return{ type: CompanyActionType.CompanyUpdate, payload: company}
}

export function companyDeleteAction(id: number):CompanyAction{
    return{ type:CompanyActionType.CompanyDelete, payload: id}
}

export function companyCouponUpdateAction():CompanyAction{
    return{ type:CompanyActionType.CompanyCouponUpdate}
}

//reducer
export function companyReducer(currentState: CompanyState = new CompanyState(), action: CompanyAction):CompanyState{
    const newState = {...currentState};

    switch (action.type){
        case CompanyActionType.CompanyDownloaded:
            newState.companies = action.payload;
            break;

        case CompanyActionType.CompanyAdd:
            newState.companies = [];
            break;

        case CompanyActionType.CompanyUpdate:
            newState.companies = [];
            break;
        
        case CompanyActionType.CompanyDelete:
            newState.companies = [];
            break;
        case CompanyActionType.CompanyCouponUpdate:
            newState.companies = [];
             break;
    }

    return newState;

}