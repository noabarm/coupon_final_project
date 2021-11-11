import { Popover} from "@mui/material";
import { useState } from "react";
import { useHistory } from "react-router";
import UpdateCompany from "../../adminComponents/updateCompany/updateCompany";
import { companyDeleteAction } from "../../redux/CompanyState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import notify from "../../utils/Notify";
import "./singleCompany.css";

interface SingleCompanyProps{
    name:string,
    email:string,
    password:string,
    id:number,
}

function SingleCompany(props: SingleCompanyProps): JSX.Element {
    let history = useHistory();
    const myUrl = globals.urls.administrator + "deleteCompany/";

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
        history.push("/companyCoupnsList/"+props.id);
    }
    
    function deleteCompany(){
        jwtAxios.delete(myUrl+props.id).then((response)=>{
            console.log(response.config.headers.authorization);
            store.dispatch(companyDeleteAction(props.id));
            notify.success("company was deleted successfully!");
            history.push("/allCompanies");
        }).catch(error=>{console.log(error)});  
    }

   
    return (
        <div className="singleCompany companyBox">
            <div className="companyColum">
            {props.id}
            </div>
            <div className="companyColum">
			{props.name}
            </div>
            <div className="companyColum">
            {props.email}
            </div>
            <div className="companyColum">
            {props.password}
            </div>
            <div className="companyColum">
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
                <UpdateCompany
                id={props.id}
                name={props.name}
                />
            </Popover>
            <div className="companyColum">
            <input className="listButton deleteButton" type="button" value="Delete" onClick={deleteCompany} />
            </div>
        </div>
    );
}

export default SingleCompany;
