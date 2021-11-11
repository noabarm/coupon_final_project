import "./singleCouponRow.css";
interface SingleCouponRowProps {
	id: number,
    category:string,
    title:string,
    description:string,
    startDate:string,
    endDate:string,
    amount:number,
    price:number,
    image:string,
    companyID:number,
}

function SingleCouponRow(props: SingleCouponRowProps ): JSX.Element {
    const imgUrl= "https://backendlessappcontent.com/AE176C35-B9A0-07F6-FF0E-242CF8C99600/ACFDB970-70A6-45A1-9877-9716DEE3861D/files/img/" + props.image;
    return (
         <div className="singleCouponRow">
           
            <div className="text">
                <div className="title">
			    {props.title}
                </div>
            <br/>
            id number : {props.id}<br/>
            company id: {props.companyID}<br/>
            category : {props.category}<br/>
            start date : {props.startDate}<br/>
            end date : {props.endDate}<br/>
            price  : {props.price}<br/>
            amount : {props.amount}<br/>
                <div className="description">
                description :{props.description}<br/>
                </div><br/><br/><br/>
                <hr/>
            </div>

            <div className="picture">
                <img src={imgUrl} width="300px" height="225px"/>
            </div>
        </div>
    );
}

export default SingleCouponRow;
