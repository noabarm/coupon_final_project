import jwtDecode from "jwt-decode";
import { useState } from "react";
import { NavLink, useHistory } from "react-router-dom";
import AuthenToken from "../../model/AuthenToken";
import store from "../../redux/store";
import { logoutUser } from "../../redux/AuthState";
//import Badge, { BadgeProps } from '@mui/material/Badge';
//import { styled } from '@mui/material/styles';
import "./signIn.css";
import { Popover } from "@mui/material";
import RegisterCustomer from "../registerCustomer/registerCustomer";
import { customerCouponUpdateAction } from "../../redux/CustomerState";
import { companyCouponUpdateAction } from "../../redux/CompanyState";


function SignIn(): JSX.Element {
   let decoded: AuthenToken;
   const logoNoa ="https://backendlessappcontent.com/AE176C35-B9A0-07F6-FF0E-242CF8C99600/ACFDB970-70A6-45A1-9877-9716DEE3861D/files/img/logoNoa.png"
   const history = useHistory();

   const[SignInUser,setUser]=useState<string>("guest");

   const logoClick =()=>{
    history.push("/openPage")
   }

   const handleClick =()=>{
    store.dispatch(logoutUser());
    store.dispatch(customerCouponUpdateAction());
    store.dispatch(companyCouponUpdateAction());
    history.push("/openPage")
   } 
  
   store.subscribe(()=>{
    console.log("start fancshion")
    if(store.getState().authState.loginUser.token === ""){
        console.log("in if")
        setUser("guest");   
    }else{
        console.log("in else")
    decoded = jwtDecode(store.getState().authState.loginUser.token);
    setUser(decoded.sub)
    }   
    })
    

    const [anchorEl, setAnchorEl] = useState<HTMLButtonElement | null>(null);
    
    const handleClickReg = (event: React.MouseEvent<HTMLButtonElement>) => {
      setAnchorEl(event.currentTarget);
    };
  
    const handleClose = () => {
        setAnchorEl(null);
     };
  
    const open = Boolean(anchorEl);
    const id = open ? 'simple-popover' : undefined;

    
    return (
        <div className="signIn">
             <img className="logo" src={logoNoa} onClick={logoClick}/> 
            <div className="users">
                <nav>
                    <NavLink className="loginNav" exact to="/loginAdmin">Admin</NavLink>
                    |
                    <NavLink className="loginNav" exact to="/loginCompany">Company</NavLink>
                    |
                    <NavLink className="loginNav" exact to="/loginCustomer">Customer</NavLink>
                </nav>
            </div>
            <div className="user">
                    Hello {SignInUser}
            </div>
            <div className="singUpButton">
                <input className="regButton" type="button" value="SignUp" onClick={handleClickReg} />
            </div>
            <Popover
                className="popoverAdd"
                id={id}
                open={open}
                anchorEl={anchorEl}
                onClose={handleClose}
                anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
                }}
                >
                <RegisterCustomer/>
            </Popover>
            <NavLink className="logoutNav" exact to="/openPage" onClick={handleClick}>|  Logout</NavLink>
            {/*
            <IconButton aria-label="cart">
                <StyledBadge badgeContent={4} color="secondary">
                <ShoppingCartIcon />
                </StyledBadge>
            </IconButton> 
            <img className="logo" src={logoNoa} onClick={logoClick}/> */}
        </div>
    );
}

export default SignIn

