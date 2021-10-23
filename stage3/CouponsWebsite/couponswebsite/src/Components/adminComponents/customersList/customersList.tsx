import { IconButton, Popover } from "@mui/material";
import { SyntheticEvent, useEffect, useState } from "react";
import { useHistory } from "react-router";
import CustomerDetails from "../../model/CustomerDetails";
import { customerDownloadedAction } from "../../redux/CustomerState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import notify from "../../utils/Notify";
import "./customersList.css";
import SearchIcon from '@mui/icons-material/Search';
import SingleCustomer from "../../mainComponent/singleCustomer/singleCustomer";
import AddCustomer from "../addCustomer/addCustomer";
import AuthenToken from "../../model/AuthenToken";
import jwtDecode from "jwt-decode";

interface CustomersList{
    arrayCustomerDetails:CustomerDetails[]
}

function CustomersList(): JSX.Element {
    
        const myUrl = globals.urls.administrator + "customers/all"
    
        const [arrayCustomerDetails,setData] = useState([]);
    
        const [anchorEl, setAnchorEl] = useState<HTMLButtonElement | null>(null);
    
        const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
        };
    
        const handleClose = () => {
          setAnchorEl(null);
        };
    
        const open = Boolean(anchorEl);
        const id = open ? 'simple-popover' : undefined;
    
    
        const history = useHistory();
    
        let myNumber:number=0;
    
        var decoded: AuthenToken;
        
        function updateNumber(args:SyntheticEvent){
            myNumber = Number((args.target as HTMLInputElement).value);
            console.log(myNumber);
        }
    
        function searchCustomer(){
            console.log(arrayCustomerDetails);
            var arrayOneCustomer = arrayCustomerDetails.filter(function(item){
                return item.id === myNumber;
            });
            
            if(arrayOneCustomer.length<1){
                notify.error("customer was not found !!!");
                return
            }
            history.push("/searchCustomer/"+myNumber);
    
            notify.success("customer was found in the system !!!");
        }
    
        
        useEffect(()=>{
            decoded = jwtDecode(localStorage.getItem("token"));

            console.log(decoded.clientType);
    
            if (decoded.clientType !== "administrator"){
               notify.error("You are not allowed to enter this page!!")
               history.push("/openPage");    
            }
            
            console.log(store.getState().customerState);
            if(store.getState().customerState.customers.length===0){
                jwtAxios.post(myUrl).then((response)=>{
                    setData(response.data);
                    store.dispatch(customerDownloadedAction(response.data))})
                    .catch(error=>console.log(error));
            }else{
                setData(store.getState().customerState.customers);
            }
            console.log(arrayCustomerDetails);
        });
    
    
        return (
            <div className="customersList">
                <header>
                    <div className="searchCustomerButton">
                        <IconButton className="searchCusIcon" onClick={searchCustomer}>
                        <SearchIcon style={{fontSize:25}}/>
                        </IconButton>
                         <input className ="searchCusPlace" type="number" placeholder="customer id" onChange={updateNumber}/>
                    </div>
                    <div className="headerChartAdminCus">
                        <div className="Colum">
                        Id number
                        </div>
                        <div className="Colum">
                        First Name:
                        </div>
                        <div className="Colum">
                        Last Name:
                        </div>
                        <div className="Colum">
                        Email:
                        </div>
                        <div className="Colum">
                        Password
                        </div>
                    </div>
                </header>   
                <main>
                {arrayCustomerDetails.map(item=><SingleCustomer
                    key={item.email}
                    id={item.id}
                    firstName={item.firstName}
                    lastName={item.lastName}
                    email={item.email}
                    password={item.password}
                    />)}
                </main>
                <footer>
                <br/><br/>
                <div className="addCusButton">
                <input className="addButton" type="button" value="Add Customer" onClick={handleClick} />
                </div>
                <Popover
                    className="popoverAdd"
                    id={id}
                    open={open}
                    anchorEl={anchorEl}
                    onClose={handleClose}
                    anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'left',
                    }}
                >
                    <AddCustomer/>
                </Popover>
                </footer>
            </div>
        );
    }
    
export default CustomersList;
