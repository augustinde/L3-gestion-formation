import React, {useEffect, useState} from "react";
import './Profil.css';
import {Navigate} from "react-router-dom";

export default function Profil(props){

    const [user, setUser] = useState({});
    const [confirmPassword, setConfirmPassword] = useState("");

    useEffect(() => {
        console.log(JSON.parse(window.sessionStorage.getItem("user")));
        setUser(JSON.parse(window.sessionStorage.getItem("user")));
    },[])

    const onChangeInput = e => {
        const {name, value} = e.target;
        setUser(prevState => ({
            ...prevState,
            [name]: value
        }));
        console.log(user)
    }

    const test = () => {
        confirmPassword === user.userPassword ? console.log("OK mdp") : console.log("Nop mdp")
    }


    return (
        <>
            <div className="d-flex h-100 text-center text-white bg-dark align-items-center">
                <div className="cover-container d-flex w-100 p-3 mx-auto flex-column">
                    <h1>Votre profil</h1>
                    <br/>
                    <table className="table table-dark table-striped">
                        <tbody>
                            <tr>
                                <th>Pseudo</th>
                                <td>
                                    <input type="text"
                                           name={"userName"}
                                           value={user.userName}
                                           className="form-control form-control-sm"
                                           onChange={onChangeInput}/>
                                </td>
                            </tr>
                            <tr>
                                <th>Email</th>
                                <td>
                                    <input type="email"
                                           name={"userEmail"}
                                           value={user.userEmail}
                                           className="form-control form-control-sm"
                                           onChange={onChangeInput}/>
                                </td>
                            </tr>
                            <tr>
                                <th>Mot de passe</th>
                                <td>
                                    <input type="password"
                                           name={"userPassword"}
                                           value={user.userPassword}
                                           className="form-control form-control-sm"
                                           onChange={onChangeInput}/>
                                </td>
                            </tr>
                            <tr>
                                <th>Confirmation mot de passe</th>
                                <td>
                                    <input type="password"
                                           value={confirmPassword}
                                           className="form-control form-control-sm"
                                           onChange={e => setConfirmPassword(e.target.value)}/>
                                </td>
                            </tr>
                            <tr>
                                <th>Role</th>
                                <td>{user.userRole}</td>
                            </tr>
                            <tr>
                                <td colSpan={"2"}>
                                    <button className={"btn btn-secondary"} onClick={test}>Modifier</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </>
    );

}