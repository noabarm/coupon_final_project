import { Popover, Typography } from "@mui/material";
import React from "react";
import { useState } from "react";
import { useHistory } from "react-router";
import { companyDeleteAction } from "../../redux/CompanyState";
import { couponDeleteAction } from "../../redux/CouponState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import notify from "../../utils/Notify";
import UpdateCoupon from "../updateCoupon/updateCoupon";
import "./singleCouponComp.css";

interface SingleCouponCompProps{
    amount:number,
    category:string,
    companyID:number,
    description:string,
    endDate:string,
    id:number,
    image:string,           
    price:number,
    startDate:string,   
    title:string,
}

function SingleCouponComp(props:SingleCouponCompProps): JSX.Element {
    let history = useHistory();
    const myUrl = globals.urls.company + "deleteCoupon/";
    const imgUrl = "https://backendlessappcontent.com/AE176C35-B9A0-07F6-FF0E-242CF8C99600/ACFDB970-70A6-45A1-9877-9716DEE3861D/files/img/"+props.image;
    const [anchorEl, setAnchorEl] = useState<HTMLButtonElement | null>(null);

    const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
      setAnchorEl(null);
    };

    const open = Boolean(anchorEl);
    const id = open ? 'simple-popover' : undefined;

    //popOver image
    const [anchorElm, setAnchorElm] = React.useState<HTMLElement | null>(null);

    const handlePopoverOpen = (event: React.MouseEvent<HTMLElement>) => {
      setAnchorElm(event.currentTarget);
    };
  
    const handlePopoverClose = () => {
      setAnchorElm(null);
    };
  
    const open1 = Boolean(anchorElm);

    function deleteCoupon(){
        jwtAxios.delete(myUrl+props.id).then((response)=>{
            console.log(response.config.headers.authorization);
            store.dispatch(companyDeleteAction(props.companyID));
            store.dispatch(couponDeleteAction(props.id))
            notify.success("coupon was deleted successfully!");
            console.log("coupon was delete")
            history.push("/allCoupons");
            console.log("coupon was delete")
        })
    }
    return (
        <div className="singleCouponComp companyBox">
            {/*
            <div className="couponColum">
            {props.id}
            </div>
            <div className="couponColum">
			{props.companyID}
            </div>
            */}
            <div className="couponColum">
			{props.category}
            </div>
            <div className="couponColum">
            {props.title}
            </div>
            <div className="columDescription">
            {props.description}
            </div>
            <div className="couponColum">
            {props.startDate}
            </div>
            <div className="couponColum">
            {props.endDate}
            </div>
            <div className="couponColumPrice">
            {props.price}
            </div>
            <div className="couponColum">
            {props.amount}
            </div>
            <div>
            <Typography
             aria-owns={open1 ? 'mouse-over-popover' : undefined}
            aria-haspopup="true"
            onMouseEnter={handlePopoverOpen}
            onMouseLeave={handlePopoverClose}
            >
            <div className="couponColum">
            {props.image}
            </div>
            </Typography>
            <Popover
            className="popoverImg"
            id="mouse-over-popover"
            sx={{
            pointerEvents: 'none',
            }}
            open={open1}
            anchorEl={anchorElm}
            anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'left',
             }}
            transformOrigin={{
            vertical: 'top',
            horizontal: 'left',
                }}
            onClose={handlePopoverClose}
                disableRestoreFocus
      >
            <Typography className="Timg" sx={{ p: 1 }}>
                <img id="img1" src={imgUrl} />
            </Typography>
            </Popover>
            </div>
            <div className="couponColum">
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
                <UpdateCoupon
                id={props.id}
                />
            </Popover>
            <div className="couponColum">
            <input className="listButton deleteButton" type="button" value="Delete" onClick={deleteCoupon} />
            </div>
			
        </div>
    );
}

export default SingleCouponComp;
