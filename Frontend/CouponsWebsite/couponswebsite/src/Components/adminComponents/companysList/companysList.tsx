import { SyntheticEvent, useEffect, useState } from "react";
import { useHistory } from "react-router";
import SingleCompany from "../../mainComponent/singleCompany/singleCompany";
import { companyDownloadedAction } from "../../redux/CompanyState";
import store from "../../redux/store";
import globals from "../../utils/Globals";
import jwtAxios from "../../utils/JWTAxios";
import "./companysList.css";
import SearchIcon from '@mui/icons-material/Search';
import { IconButton, Popover } from "@mui/material";
import notify from "../../utils/Notify";
import AddCompany from "../addCompany/addCompany";
import AuthenToken from "../../model/AuthenToken";
import jwtDecode from "jwt-decode";




function CompanysList(): JSX.Element {
    const myUrl = globals.urls.administrator + "companies/all"

    const [arrayCompanyDetails,setData] = useState([]);

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

    function searchCompany(){
        
        console.log(arrayCompanyDetails);
        var arrayOneCompany = arrayCompanyDetails.filter(function(item){
            return item.id === myNumber;
        });

        if(arrayOneCompany.length<1){
            notify.error("company was not found !!!");
            return
        }
        history.push("/searchCompany/"+myNumber);
        
        notify.success("company was found in the system !!!");
    }

    
    useEffect(()=>{
        decoded = jwtDecode(localStorage.getItem("token"));

        console.log(decoded.clientType);

        if (decoded.clientType !== "administrator"){
           notify.error("You are not allowed to enter this page!!")
           history.push("/openPage");    
        }
        
        console.log(store.getState().companyState);
        if(store.getState().companyState.companies.length ===0){
            jwtAxios.post(myUrl)
                .then((response)=>{
                setData(response.data);
                store.dispatch(companyDownloadedAction(response.data))})
                .catch(error=>console.log(error));
        }else{
            setData(store.getState().companyState.companies);
        }
        console.log(arrayCompanyDetails);
    });


    return (
        <div className="companysList">
            <header>
                <div className="searchCompButton">
                    <IconButton className="searchCompIcon" onClick={searchCompany}>
                    <SearchIcon style={{fontSize:25}}/>
                    </IconButton>
                    <input className ="searchPlaceCompany" type="number" placeholder="company id" onChange={updateNumber}/>
                </div>
                <div className="headerChartAdmin">
                    <div className="Colum">
                    Id number
                    </div>
                    <div className="Colum">
			        Name:
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
                {arrayCompanyDetails.map(item=><SingleCompany
                key={item.email}
                id={item.id}
                name={item.name}
                email={item.email}
                password={item.password}
                />)}
                <br/><br/>
            </main>
            <footer>
            <div className="addCompButton">
            <input className="addButton" type="button" value="Add Company" onClick={handleClick} />
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
                <AddCompany/>
            </Popover>
            </footer>
        </div>
    );
}

export default CompanysList;
