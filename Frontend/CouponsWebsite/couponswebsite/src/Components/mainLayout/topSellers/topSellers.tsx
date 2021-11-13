import "./topSellers.css";
import ListCouponsFun from "../../Axios/ListCouponsFun/ListCouponsFun";
import { useEffect, useState } from "react";
import globals from "../../utils/Globals";
import store from "../../redux/store";
import axios from "axios";
import { couponDownloadedAction } from "../../redux/CouponState";
import CouponDetails from "../../model/CouponDetails";
import { Box, FormControl, InputLabel, MenuItem, Select, SelectChangeEvent, Slider } from "@mui/material";
import notify from "../../utils/Notify";

function TopSellers(): JSX.Element {
    const myUrl = globals.urls.guest + "coupons/all";

    const [fullCouponDetails,setData] = useState([]);
    
    const [couponsCompanyDetails,setCouponData] = useState([]);

    let sortByCategory: CouponDetails[]=[];
   
    const [selectedCategory, setCategory] = useState('start');

    const [price, setPrice] = useState<number | number[]>(5000);

    const marks =[
        {
          value:0,
          label:0,
        },
        {
            value:5000,
            label:5000,
        },
        {
            value:2500,
            label:"price",
        },

    ]

    const handlePriceChange =(event: Event, value: number | number[], activeThumb: number) =>{
        console.log(value);
        console.log(activeThumb);
        setPrice(value)
        searchCoupon(selectedCategory,value);
    }

    const handleChange = (selectedCategory: SelectChangeEvent) => {
        setCategory(selectedCategory.target.value as string);
        searchCoupon(selectedCategory.target.value as string,price);
    };


    function searchCoupon(onlineCategory:string , price:number | number[]){

        if(onlineCategory === "all"){
            sortByCategory = fullCouponDetails.filter(function(item){
                return (item.price<=price); 
            })
        }else{
            sortByCategory = fullCouponDetails.filter(function(item){
                return (item.category === onlineCategory && item.price<=price); 
            })
        }
        /*
        if(sortByCategory.length ===0 && onlineCategory !=="all" ){
            notify.error("coupons were not found in the system !!!");

        }
        */
        setCouponData(sortByCategory);      
    }

    
    useEffect(()=>{
        if (store.getState().couponState.coupons.length===0){
            axios.get(myUrl).then((response)=>{
                setData(response.data);
                store.dispatch(couponDownloadedAction(response.data))});
        } else {
            setData(store.getState().couponState.coupons);
        }

        if(store.getState().couponState.coupons.length===0){
            axios.get(myUrl).then((response)=>{
                console.log(response.data);
                setData(response.data);
                store.dispatch(couponDownloadedAction(response.data));
                if(selectedCategory === "start"){
                    setCouponData(store.getState().couponState.coupons);
                    } 
               
            }).catch(error=>{console.log(error)}); 
        }else{
            console.log("in else");
            if(selectedCategory === "start"){
                setCouponData(store.getState().couponState.coupons);
            } 
           
        }            
    });
    
    return (
        <div className="topSellers"> 
			<div className = "header">
                Top Sellers
            </div>
            <div className="searchCouponButton">
                    <Box id = "SliderBox" width={300}>
                        <Slider id="priceSlider" 
                        size="small"
                        defaultValue={5000}
                        aria-label="Small"
                        valueLabelDisplay="auto"
                        max={5000}
                        onChange={handlePriceChange}
                        marks={marks}
                        />
                    </Box>
                    <Box id = "categoryBox" sx={{ minWidth: 120 }}>
                    <FormControl id = "categoryList" fullWidth>
                        <InputLabel id="demo-simple-select-label">Category</InputLabel>
                        <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        value={selectedCategory}
                        label="Search by Category"
                        onChange={handleChange}
                        >
                        <MenuItem value={"all"}>all</MenuItem>
                        <MenuItem value={"food"}>food</MenuItem>
                        <MenuItem value={"electricity"}>electricity</MenuItem>
                        <MenuItem value={"restaurant"}>restaurant</MenuItem>
                        <MenuItem value={"vacation"}>vacation</MenuItem>
                        <MenuItem value={"sport"}>sport</MenuItem>
                        </Select>
                    </FormControl>
                    </Box>
                </div>
            <br/><br/>
             <ListCouponsFun arrayCouponDetails={couponsCompanyDetails} />
        </div>
    );
}

export default TopSellers;
