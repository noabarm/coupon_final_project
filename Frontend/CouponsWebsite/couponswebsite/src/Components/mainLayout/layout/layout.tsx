import { BrowserRouter } from "react-router-dom";
import Footer from "../footer/footer";
import Header from "../header/header";
import Routing from "../Routing/Routing";
import "./layout.css";
import 'scrollable-component';
import globals from "../../utils/Globals";
import { useEffect } from "react";
import axios from "axios";
import store from "../../redux/store";
import { couponDownloadedAction } from "../../redux/CouponState";

function Layout(): JSX.Element {
    
    const myUrl = globals.urls.guest + "coupons/all";
    useEffect(()=>{
        axios.get(myUrl)
        .then(response => {
            store.dispatch(couponDownloadedAction(response.data))
        }).catch(error=>{
            console.log(error)});
    })

    return (
        <div className="layout">
            <BrowserRouter>
                <header>
                    <Header/>
                </header>
                <main className="mainLayout">
                    <Routing/>
                </main>
                <footer>
                    <Footer/>
                </footer>
            </BrowserRouter>
        </div>
    );
}

export default Layout;
