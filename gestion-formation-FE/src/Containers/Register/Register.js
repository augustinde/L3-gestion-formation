import React, {useContext, useState} from "react";
import './Register.css'
import InputFloating from "../../Components/InputFloating/InputFloating";
import Form from "../../Components/Form/Form";
import {LoginContext} from "../../Context/LoginContext";
import axios from "axios";
import {Navigate} from "react-router-dom";

export default function Register(){

	const [email, setEmail] = useState("")
	const [password, setPassword] = useState("")
	const [passwordConfirm, setPasswordConfirm] = useState("")
	const [username, setUsername] = useState("")

	const handleSubmit = async e => {
		e.preventDefault();

		(password === passwordConfirm && password.length >= 5) ? console.log("Pass ok") : console.log("Pass error")


		axios.get("http://localhost:8080/gestion-formation-BE/api/register/" + username + "/"+email+"/"+password)
			.then((response) => {
				console.log(response)
		});
	}

    return (
        <>
			<div className="d-flex h-100 text-center text-white bg-dark align-items-center">
				<div className="cover-container d-flex w-100 p-3 mx-auto flex-column">
					<Form labelButton={"S'inscrire"} onSubmit={handleSubmit}>
						<h1 className="h3 mb-3 fw-normal">Inscription</h1>
						<InputFloating id="floatingInputFirstName"
									   type="text"
									   placeholder={"Gotaga"}
									   labelContent={"Nom d'utilisateur"}
									   onChange={e => setUsername(e.target.value)}/>


						<InputFloating id="floatingInputEmail"
									   type="email"
									   placeholder={"email@exemple.com"}
									   labelContent={"Email"}
									   onChange={e => setEmail(e.target.value)}/>


						<InputFloating id="floatingInputPassword"
									   type="password"
									   placeholder={"Mot de passe"}
									   labelContent={"Mot de passe"}
									   onChange={e => setPassword(e.target.value)}/>


						<InputFloating id="floatingInputPasswordConfirm"
									   type="password"
									   placeholder={"Confirmez votre mot de passe"}
									   labelContent={"Confirmez votre mot de passe"}
									   onChange={e => setPasswordConfirm(e.target.value)}/>


					</Form>
				</div>
			</div>
        </>
    );

}