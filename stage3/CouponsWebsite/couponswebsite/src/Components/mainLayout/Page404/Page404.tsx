import "./Page404.css";
//import page404woman from "../../../Assetes/Images/page404woman.jpg"

function Page404(): JSX.Element {
    const page404img = "https://backendlessappcontent.com/AE176C35-B9A0-07F6-FF0E-242CF8C99600/ACFDB970-70A6-45A1-9877-9716DEE3861D/files/img/page404woman.jpg"
    return (
        <div className="Page404">
			<img src={page404img} width="40%" height="40%"/>
        </div>
    );
}

export default Page404;
