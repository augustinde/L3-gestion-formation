import React, {useContext, useEffect} from "react";
import "./Logout.css";
import {AuthContext} from "../../Context/AuthContext";
import {Navigate} from "react-router-dom";
import Container from "../../Components/Container/Container";

export default function Logout(){

    const {user, addUser} = useContext(AuthContext);

    useEffect(() => {
        addUser(null)
        localStorage.removeItem("user");
    }, [])

    return (
        <>
            {user === null && <Navigate to={"/login"}/>}

            <Container>
                <p>Déconnexion !</p>
            </Container>
        </>
    );

}