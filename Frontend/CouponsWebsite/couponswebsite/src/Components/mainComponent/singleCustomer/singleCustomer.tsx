import { Popover } from "@mui/material";
import { useState } from "react";
import { useHistory } from "react-router-dom";
import UpdateCustomer from "../../adminComponents/updateCustomer/updateCustomer";
import { customerDeleteAction } from "../../redux/CustomerState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import notify from "../../utils/Notify";
import "./singleCustomer.css";
interface SingleCustomerProps{
    lastName:string,
    firstName:string,
    email:string,
    password:string,
    id:number,
}

function SingleCustomer(props: SingleCustomerProps): JSX.Element {
    let history = useHistory();
    const myUrl = globals.urls.administrator + "deleteCustomer/";

    const [anchorEl, setAnchorEl] = useState<HTMLButtonElement | null>(null);

    const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
      setAnchorEl(null);
    };

    const open = Boolean(anchorEl);
     const id = open ? 'simple-popover' : undefined;

    
    
    function showCoupons(){
        history.push("/customerCoupnsList/"+props.id);
    }
    
    function deleteCustomer(){
        jwtAxios.delete(myUrl+props.id).then((response)=>{
            console.log(response.config.headers.authorization);
            store.dispatch(customerDeleteAction(props.id));
            notify.success("customer was deleted successfully!");
            history.push("/allCustomers");
        }).catch(error=>console.log(error));
    }

   
    return (
        <div className="singleCustomer companyBox">
            <div className="custumerColum">
            {props.id}
            </div>
            <div className="custumerColum">
			{props.firstName}
            </div>
            <div className="custumerColum">
			{props.lastName}
            </div>
            <div className="custumerColum">
            {props.email}
            </div>
            <div className="custumerColum">
            {props.password}
            </div>
            <div className="custumerColum">
            <input className="listButton" type="button" value="Coupns" onClick={showCoupons} />
            </div>
            <div className="companyColum">
            <input className="listButton" type="button" value="Update" onClick={handleClick} />
            </div>
            <Popover
                className="popoverUpdate"
                id={id}
                open={open}
                anchorEl={anchorEl}
                onClose={handleClose}
                anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
                }}
            >
                <UpdateCustomer
                id={props.id}
                />
            </Popover>
            <div className="companyColum">
            <input className="listButton deleteButton" type="button" value="Delete" onClick={deleteCustomer} />
            </div>
        </div>
    );
}

export default SingleCustomer;


