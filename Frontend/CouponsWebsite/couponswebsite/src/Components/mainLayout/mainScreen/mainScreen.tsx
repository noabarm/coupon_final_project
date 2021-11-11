import BannerImg from "../bannerImg/bannerImg";
import TopSellers from "../topSellers/topSellers";
import "./mainScreen.css";

function MainScreen(): JSX.Element {
    return (
        <div className="mainScreen">
            <header>
                <BannerImg/>
            </header>
            <main>
                <TopSellers/>
            </main>
			
        </div>
    );
}

export default MainScreen;
