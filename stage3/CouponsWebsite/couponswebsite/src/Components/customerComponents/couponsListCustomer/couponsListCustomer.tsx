import { Box, FormControl, InputLabel, MenuItem, Select, SelectChangeEvent, Slider } from "@mui/material";
import jwtDecode from "jwt-decode";
import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import SingleCouponRow from "../../adminComponents/singleCouponRow/singleCouponRow";
import AuthenToken from "../../model/AuthenToken";
import CouponDetails from "../../model/CouponDetails";
import CustomerDetails from "../../model/CustomerDetails";
import { loginUserString } from "../../redux/AuthState";
import { customerDownloadedAction } from "../../redux/CustomerState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import notify from "../../utils/Notify";
import "./couponsListCustomer.css";

interface CouponsListCustomer{
    couponsCustomerDetails:CouponDetails[],
    customerDetails:CustomerDetails[];
}

function CouponsListCustomer(): JSX.Element {

    const myUrl = globals.urls.customer + "details";

    const [couponsCustomerDetails,setCouponData] = useState([]);

    const history = useHistory();

    var decoded: AuthenToken;
    var sortByCategory: CouponDetails[]=[];
    var couponsArray:CouponDetails[]; 
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
        couponsArray = store.getState().customerState.customers[0].coupons;
        console.log(store.getState().customerState.customers[0]);
        if(onlineCategory === "all"){
            sortByCategory = couponsArray.filter(function(item){
                return (item.price<=price); 
            })
        }else{
            sortByCategory = couponsArray.filter(function(item){
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
       
        decoded = jwtDecode(localStorage.getItem("token"));

        console.log(decoded.clientType);

        if (decoded.clientType !== "customer"){
           notify.error("You are not allowed to enter this page!!")
           history.push("/openPage");    
        }

        store.dispatch(loginUserString(localStorage.getItem("token")));

        console.log(store.getState().customerState.customers);

        if(store.getState().customerState.customers.length===0){
            jwtAxios.post(myUrl)
                .then((response)=>{
                console.log(response.data);
                let oneCustomerArray:CustomerDetails[] = []; 
                oneCustomerArray.push(response.data);
                store.dispatch(customerDownloadedAction(oneCustomerArray));
                if(selectedCategory === "start"){
                    setCouponData(store.getState().customerState.customers[0].coupons);
                    } 
               
            }).catch(error=>{console.log(error)}); 
        }else{
            console.log("in else");
            if(selectedCategory === "start"){
            setCouponData(store.getState().customerState.customers[0].coupons);
            } 
           
        }                        
    });

    return (
        <div className="couponsListCustomer">
            <header>
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
                <h1>list of coupons you purchase:</h1>
            </header>
            <main>
                {couponsCustomerDetails.map(item=><SingleCouponRow 
                key={item.id}
                id={item.id}
                category={item.category}
                title={item.title} 
                endDate={item.endDate}
                startDate={item.startDate}
                amount={item.amount} 
                price={item.price} 
                image={item.image}
                description={item.description}
                companyID={item.companyID}              
                />)}
            </main>
            <br/><br/>
        </div>
    );
}

export default CouponsListCustomer;
