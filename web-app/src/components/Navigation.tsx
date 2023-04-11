import {Outlet, useNavigate} from "react-router-dom";

export const Navigation = () => {
    const navigate = useNavigate();

    const navigateTo = (route: string) => {
        navigate(route);
    }

    return (
        <div className="navigation">
            <div className="navbar-frame">
                <div className="navbar">
                    <button onClick={() => navigateTo('/chats')}>Meine Chats</button>
                    <button onClick={() => navigateTo('/chat/create')}>Neuer Chat</button>
                </div>
            </div>
            <Outlet/>
        </div>
    )
}