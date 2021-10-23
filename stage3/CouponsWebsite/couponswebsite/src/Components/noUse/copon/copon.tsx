import axios from "axios";
import { Component } from "react";
import "./copon.css";

interface CoponState {
	id: number,
    companyID:number,
    category:string,
    title:string,
    description: string,
    startDate:Date,
    endDate:string,
    amount:number,
    price:number,
    image:string
}

class Copon extends Component<{}, CoponState> {
    private myUrl = "http://localhost:8080/user/coupons/all";
    public constructor(props: {}) {
        super(props);
        this.state = {
            id: 0,
            companyID:0,
            category:"",
            title:"",
            description: "",
            startDate:new Date(),
            endDate:"",
            amount:0,
            price:0,
            image:""
        };
    }

    private getData = async () =>{
        const result = await axios.get(this.myUrl);
        const coponData = result.data[0];
        console.log(result.data[0]);
        this.setState({
            id: coponData.id,
            companyID:coponData.companyID,
            category:coponData.category,
            title:coponData.title,
            description: coponData.description,
            startDate:coponData.startDate,
            endDate:coponData.endDate,
            amount:coponData.amount,
            price:coponData.price,
            image:coponData.image
        })
        
    }

    public componentDidMount():void{
       this.getData();
    }

    public render(): JSX.Element {
        return (
            <div className="copon couponBox">
				{this.state.image}<hr/>
                {this.state.title}<br/>
                {this.state.category}<br/>
                {this.state.price}<br/>
                {this.state.amount}<br/>
                {this.state.endDate}
    
            </div>
        );
    }
}

export default Copon;
