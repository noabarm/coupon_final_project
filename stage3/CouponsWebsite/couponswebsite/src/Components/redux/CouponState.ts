import CouponDetails from "../model/CouponDetails";

//our redux state
export class CouponState{
    public coupons:CouponDetails[] = [];
}

//action types
export enum CouponActionType{
    CouponDownloaded = "CouponDownloaded",
    CouponAdd = "CouponAdd",
    CouponUpdate = "CouponUpdate",
    CouponDelete = "CouponDelete",
}

//action decleration
export interface CouponAction{
    type: CouponActionType,
    payload?: any,
}

//action functions
export function couponDownloadedAction(coupons : CouponDetails[]):CouponAction{
    return {type : CouponActionType.CouponDownloaded , payload: coupons}
}

export function couponAddAction(coupon : CouponDetails):CouponAction{
    return{type: CouponActionType.CouponAdd , payload: coupon}
}

export function couponUpdateAction( coupon : CouponDetails):CouponAction{
    return {type: CouponActionType.CouponUpdate , payload: coupon}
}

export function couponDeleteAction(id: number): CouponAction{
    return {type: CouponActionType.CouponDelete , payload: id}
}

//reducer
export function couponReducer(currentState: CouponState = new CouponState(), action: CouponAction):CouponState{
    const newState = {...currentState};

    switch (action.type){
        case CouponActionType.CouponDownloaded:
            newState.coupons = action.payload;
            break;

        case CouponActionType.CouponAdd:
            newState.coupons =[];
            break;

        case CouponActionType.CouponUpdate:
            newState.coupons =[];
            break;
        
        case CouponActionType.CouponDelete:
            newState.coupons =[];
            break;
    }

    return newState;

}