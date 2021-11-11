import { Button, IconButton, MenuItem, Select, Stack, styled, TextField } from "@mui/material";
import { useState } from "react";
import { useForm } from "react-hook-form";
import CouponDetails from "../../model/CouponDetails";
import { couponAddAction } from "../../redux/CouponState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import notify from "../../utils/Notify";
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import DesktopDatePicker from '@mui/lab/DesktopDatePicker';
import Backendless from "backendless";
import UploadIcon from '@mui/icons-material/Upload';

import "./AddCoupon.css";
import { companyCouponUpdateAction } from "../../redux/CompanyState";
import { PhotoCamera } from "@mui/icons-material";
interface AddCouponProps{
    companyId:number,
}

function AddCoupon(props:AddCouponProps): JSX.Element {
    const {register, handleSubmit, formState: { errors }} = useForm<CouponDetails>();

    const myUrl = globals.urls.company + "addCoupon";

    const [startDate, setStartDate] = useState<string>()

    const [endDate, setEndDate] = useState<string>()

    const [imgName, setName] = useState<string>()

    const fixStartDate = (newDate:Date)=>{
        var day = newDate.getDate().toString();
        var mouth =(newDate.getMonth()+1).toString();
        var year = newDate.getFullYear().toString();
        if(day.length<2){
            day = "0"+day;
        }
        var startDate1 =year+"-"+mouth+"-"+day;
        console.log(startDate1);
        setStartDate(startDate1);
    }

    const fixEndDate = (newDate:Date)=>{
        var day = newDate.getDate().toString();
        var mouth =(newDate.getMonth()+1).toString();
        var year = newDate.getFullYear().toString();
        if(day.length<2){
            day = "0"+day;
        }
        var endDate1 =year+"-"+mouth+"-"+day;
        console.log(endDate1);
        setEndDate(endDate1);
    }

    const fileHandler = (myEvent:any)=>{
        console.log(myEvent);
        
        var file : File =  myEvent.target.files[0];
        setName(file.name);
        Backendless.initApp("AE176C35-B9A0-07F6-FF0E-242CF8C99600","ACFDB970-70A6-45A1-9877-9716DEE3861D");
        Backendless.Files.upload(file, "img", true )
        .then(  fileURLs =>{
           console.log( "File successfully uploaded. Path to download: " + fileURLs);
          
           console.log(imgName);
         })
        .catch(  error =>{
           console.log( "error - " + error.message );
        })
    }

    const Input = styled('input')({
        display: 'none',
    });
      
    

    function send(couponDetails:CouponDetails){
        couponDetails.startDate=startDate;
        couponDetails.endDate=endDate;
        couponDetails.image=imgName;
        couponDetails.companyID=props.companyId;
        console.log(couponDetails);
        jwtAxios.post(myUrl,couponDetails).then((response)=>{
            console.log(response.config.headers.authorization);
            store.dispatch(couponAddAction(couponDetails));
            store.dispatch(companyCouponUpdateAction());
            notify.success("coupon was add successfully!");
        }).catch(error=>{
            console.log(error);
            notify.error(error.response.data.description)});
    }
            
    return (
        <div className="AddCoupon">
			<h3>Add new Coupon</h3>
                <form onSubmit={handleSubmit(send)}>
                    <TextField label="title" variant="outlined"
                        {...register("title", {
                            required: {value: true, message: "this field is required"},
                            minLength: {value: 2,  message: "min length is 2 letters"},
                            maxLength: {value: 40, message:"max length is 40 letters"},
                    })}/>
                     <span> {errors.title && <p>{errors.title.message}</p>}</span>
                    <br/><br/>
                    <div className="categoryLabel">category:</div>
                    <Select label="category"  style={{width:195}} {...register("category",{required : true})}>
                        <MenuItem value={"food"} >food</MenuItem>
                        <MenuItem value={"electricity"}>electricity</MenuItem>
                        <MenuItem value={"restaurant"}>restaurant</MenuItem>
                        <MenuItem value={"vacation"}>vacation</MenuItem>
                        <MenuItem value={"sport"}>sport</MenuItem>
                    </Select>
                    <br/><br/>
                    <TextField label="description" variant="outlined"
                        {...register("description", {
                            required: {value: true, message: "this field is required"},
                            minLength: {value: 2,  message: "min length is 2 letters"},
                            maxLength: {value: 400, message:"max length is 400 letters"},
                    })}/>
                     <span> {errors.description && <p>{errors.description.message}</p>}</span>
                    <br/><br/>

                     <TextField type="number" label="price" variant="outlined"
                        {...register("price", {
                            required: {value: true, message: "this field is required"},
                            minLength: {value: 1,  message: "minimum price is 1 NIS"},
                    })}/>
                     <span> {errors.price && <p>{errors.price.message}</p>}</span>
                    <br/><br/>

                    <TextField type="number" label="amount" variant="outlined"
                        {...register("amount", {
                            required: {value: true, message: "this field is required"},
                            minLength: {value: 1,  message: "minimum amount is 1"},
                    })}/>
                     <span> {errors.price && <p>{errors.price.message}</p>}</span>
                    <br/><br/>

                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <Stack spacing={3}>
                            <DesktopDatePicker
                                label="Start Date"
                                value={startDate}
                                minDate={new Date('2017-01-01')}
                                onChange={fixStartDate}
                                renderInput={(params) => <TextField {...params} />}
                             />
                        </Stack>
                    </LocalizationProvider>
                    <br/>
                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <Stack spacing={3}>
                            <DesktopDatePicker
                                label="End Date"
                                value={endDate}
                                minDate={new Date('2017-01-01')}
                                onChange={fixEndDate}
                                renderInput={(params) => <TextField {...params} />}
                             />
                        </Stack>
                    </LocalizationProvider>
                    <br/>
                 
                    upload image:
                    <Stack id="uploadImg" direction="row" alignItems="center" spacing={2}>
                        
                         <label htmlFor="icon-button-file">
                            <Input accept="image/*" id="icon-button-file" type="file" onChange={fileHandler} />
                            <IconButton  color="primary" aria-label="upload picture" component="span">
                                <UploadIcon />
                            </IconButton>
                        </label>
                    </Stack>

    
                    <Button id ="sendButton" type="submit" >Send</Button>
                   

                </form>

        </div>
    );
}

export default AddCoupon;
