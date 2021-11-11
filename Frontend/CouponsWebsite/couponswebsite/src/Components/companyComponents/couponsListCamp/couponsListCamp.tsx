import { Box, FormControl, InputLabel, MenuItem, Popover, Select, SelectChangeEvent, Slider } from "@mui/material";
import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import CouponDetails from "../../model/CouponDetails";
import { companyDownloadedAction } from "../../redux/CompanyState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import notify from "../../utils/Notify";
import "./couponsListCamp.css";
import SingleCouponComp from "../singleCouponComp/singleCouponComp";
import jwtDecode from "jwt-decode";
import AuthenToken from "../../model/AuthenToken";
import AddCoupon from "../AddCoupon/AddCoupon";
import { loginUserString } from "../../redux/AuthState";
import CompanyDetails from "../../model/CompanyDetails";


interface CompanyList{
    couponsCompanyDetails:CouponDetails[],
    companyDetails:CompanyDetails[];
}

function CouponsListCamp(): JSX.Element {
    const myUrl = globals.urls.company + "details";

    const [couponsCompanyDetails,setCouponData] = useState([]);
    const [companyId,setCompanyId] = useState<number>();


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
        couponsArray = store.getState().companyState.companies[0].coupons;
        console.log(store.getState().companyState.companies[0]);
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

        if (decoded.clientType !== "company"){
           notify.error("You are not allowed to enter this page!!")
           history.push("/openPage");    
        }

        store.dispatch(loginUserString(localStorage.getItem("token")));

        console.log(store.getState().companyState.companies);

        if(store.getState().companyState.companies.length===0){
            jwtAxios.post(myUrl)
                .then((response)=>{
                console.log(response.data);
                let oneCompanyArray:CompanyDetails[] = []; 
                oneCompanyArray.push(response.data);
                setCompanyId(oneCompanyArray[0].id);
                store.dispatch(companyDownloadedAction(oneCompanyArray));
                if(selectedCategory === "start"){
                    setCouponData(store.getState().companyState.companies[0].coupons);
                    } 
               
            }).catch(error=>{console.log(error)}); 
        }else{
            setCompanyId(store.getState().companyState.companies[0].id);
            console.log("in else");
            if(selectedCategory === "start"){
            setCouponData(store.getState().companyState.companies[0].coupons);
            } 
           
        }                        
    });
    
    return (
        <div className="couponsListCamp">
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
                <div className="headerChart">
                    {/*
                    <div className="Colum">
                    Id number
                    </div>
                    <div className="Colum">
			        Company id:
                    </div>
                    */}
                    <div className="ColumCategory">
			        Category:
                    </div>
                    <div className="Colum">
                    Title:
                    </div>
                    <div className="ColumDescription">
                    Description:
                    </div>
                    <div className="Colum">
                    Start date:
                    </div>
                    <div className="Colum">
                    End date:
                    </div>
                    <div className="Colum">
                    Price:
                    </div>
                    <div className="Colum">
                    Amount:
                    </div>
                    <div className="Colum">
                    Image:
                    </div>
                </div>
            </header>
            <main>
                {couponsCompanyDetails.map(item=><SingleCouponComp
                key={item.id}
                id={item.id}
                amount={item.amount}
                category={item.category}
                companyID={item.companyID}
                description={item.description}
                endDate={item.endDate}
                image={item.image}           
                price={item.price}
                startDate={item.startDate} 
                title={item.title}
                />)}
            <br/><br/>
            </main>
            <footer>
            <div className="addCoupButton">
                <input className="addButton" type="button" value="Add Coupon" onClick={handleClick} />
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
                    <AddCoupon companyId={companyId}/>
                </Popover>
            </footer> 
        </div>     
           
    );
}

export default CouponsListCamp;



