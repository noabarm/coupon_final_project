import "./adminOpenPage.css";

function AdminOpenPage(): JSX.Element {
    return (
        <div className="adminOpenPage">
			 <div className="discriptionAdmin">
                <div className="buneerTitle">
                HELLO ADMINISTRATOR! <br/>
                </div>
                <div className="aboutUsAdmin">
                    You are in your personal page.<br/>
                    Here you can see all the companies and customers that in system.<br/>
                    To see all companies details press on companies tab,<br/>
                    also you can add/update and delete any company you want.<br/>
                    To see all customers details press on customers tab,<br/>
                    also you can add/update and delete any customer you want.<br/>
                </div>
            </div>
        </div>
    );
}

export default AdminOpenPage;
