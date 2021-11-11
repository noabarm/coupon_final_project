import SignIn from "../signIn/signIn";
import "./header.css";

function Header(): JSX.Element {
    return (
        <div className="header">
			<SignIn/>
        </div>
    );
}

export default Header;
